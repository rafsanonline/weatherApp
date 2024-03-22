package com.mdrafsanbiswas.weatherapp.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Looper
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import com.mdrafsanbiswas.weatherapp.R
import com.mdrafsanbiswas.weatherapp.ui.base.BaseActivity
import com.mdrafsanbiswas.weatherapp.ui.screens.WeatherHomeScreen
import com.mdrafsanbiswas.weatherapp.util.network_connectivity.ConnectionState
import com.mdrafsanbiswas.weatherapp.util.observeConnectivityAsFlow
import com.mdrafsanbiswas.weatherapp.util.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    val mainViewModel: MainViewModel by viewModels()
    private var disconnectStatus = -1

    private var locationCallback: LocationCallback? = null
    var fusedLocationClient: FusedLocationProviderClient? = null
    private var locationRequired = false

    private val activityResultLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            if (permissions.values.contains(false)) {
                showToast("Please allow location permission")
            } else {
                startLocationUpdates()
            }
        }

    override fun viewRelatedTask() {

        checkNetworkConnection()

        setContent {
            WeatherHomeScreen(mainViewModel)
        }

        if (isGpsEnabled(this)) {
            setUpLocationServices()
        } else {
            mainViewModel.showProgressBar = false
            showToast(getString(R.string.please_turn_your_gps_on))
        }

        mainViewModel.errorMessage.observe(this) {
            if (it.isNotEmpty()) {
                showToast(it)
            }
        }

    }

    private fun isGpsEnabled(context: Context): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }


    private fun setUpLocationServices() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.lastLocation?.let { location ->
                    Log.d("my_loc", "$location")
                    mainViewModel.updateLocation(location)
                    fusedLocationClient?.removeLocationUpdates(locationCallback!!)
                }
            }
        }

        val permissions = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )

        if (permissions.all {
                ContextCompat.checkSelfPermission(
                    this,
                    it
                ) == PackageManager.PERMISSION_GRANTED
            }) {
            startLocationUpdates()
        } else {
            activityResultLauncher.launch(permissions)
        }
    }


    private fun checkNetworkConnection() {
        lifecycleScope.launch {
            observeConnectivityAsFlow().collect {
                when (it) {
                    is ConnectionState.Available -> {
                        if (disconnectStatus != -1) {
                            Snackbar.make(
                                window.decorView.rootView,
                                getString(R.string.connection_available),
                                Snackbar.LENGTH_LONG
                            ).show()
                        }
                    }

                    is ConnectionState.Unavailable -> {
                        disconnectStatus = 1
                        Snackbar.make(
                            window.decorView.rootView,
                            getString(R.string.no_internet_connection),
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }

            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        mainViewModel.showProgressBar = true
        locationCallback?.let {
            val locationRequest = LocationRequest.create().apply {
                interval = 10000
                fastestInterval = 5000
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            }
            fusedLocationClient?.requestLocationUpdates(
                locationRequest,
                it,
                Looper.getMainLooper()
            )
        }
    }

    override fun onPause() {
        super.onPause()
        locationCallback?.let { fusedLocationClient?.removeLocationUpdates(it) }
    }
}
