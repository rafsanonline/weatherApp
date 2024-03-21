package com.mdrafsanbiswas.weatherapp.ui.main

import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.mdrafsanbiswas.weatherapp.ui.base.BaseActivity
import com.mdrafsanbiswas.weatherapp.util.network_connectivity.ConnectionState
import com.mdrafsanbiswas.weatherapp.util.observeConnectivityAsFlow
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BaseActivity() {

     val mainViewModel: MainViewModel by viewModels()
    private var disconnectStatus = -1

    override fun viewRelatedTask() {

        checkNetworkConnection()

        setContent {

        }
    }

    private fun checkNetworkConnection() {
        lifecycleScope.launch {
            observeConnectivityAsFlow().collect{
                when(it) {
                    is ConnectionState.Available -> {
                        if (disconnectStatus !=-1) {
                            Snackbar.make(window.decorView.rootView, "Connection Available", Snackbar.LENGTH_LONG).show()
                        }
                    }
                    is ConnectionState.Unavailable -> {
                        disconnectStatus = 1
                        Snackbar.make(window.decorView.rootView, "No internet connection", Snackbar.LENGTH_LONG).show()
                    }
                }

            }
        }
    }
}
