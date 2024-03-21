package com.mdrafsanbiswas.weatherapp.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.widget.Toast
import com.mdrafsanbiswas.weatherapp.ui.main.MainActivity
import com.mdrafsanbiswas.weatherapp.util.network_connectivity.ConnectionState
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import java.text.SimpleDateFormat
import java.util.*

val Context.currentConnectivityState: ConnectionState
    get() {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return getCurrentConnectivityState(connectivityManager)
    }

private fun getCurrentConnectivityState(
    connectivityManager: ConnectivityManager
): ConnectionState {
    val connected = connectivityManager.allNetworks.any { network ->
        connectivityManager.getNetworkCapabilities(network)
            ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            ?: false
    }

    return if (connected) ConnectionState.Available else ConnectionState.Unavailable
}

fun Context.observeConnectivityAsFlow() = callbackFlow {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val callback = NetworkCallback { connectionState -> trySend(connectionState) }
    val networkRequest = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .build()

    connectivityManager.registerNetworkCallback(networkRequest, callback)

    val currentState = getCurrentConnectivityState(connectivityManager)
    trySend(currentState)

    awaitClose {
        connectivityManager.unregisterNetworkCallback(callback)
    }
}

fun NetworkCallback(callback: (ConnectionState) -> Unit): ConnectivityManager.NetworkCallback {
    return object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            callback(ConnectionState.Available)
        }

        override fun onLost(network: Network) {
            callback(ConnectionState.Unavailable)
        }
    }
}

fun Context.showProgressBar() = run {
    try {
        (this as MainActivity).mainViewModel.showProgressBar = true
    } catch (e: Exception) {
        this.showToast(e.message.toString())
    }
}

fun Context.hideProgressBar() = run {
    try {
        (this as MainActivity).mainViewModel.showProgressBar = false
    } catch (e: Exception) {
        this.showToast(e.message.toString())
    }
}

fun Context.showToast(text: String, time: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(this, text, time).show()

fun Int?.formatDate(): String {
    return try {
        val timestamp = this?.toLong() ?: return "Invalid Timestamp"
        val date = Date(timestamp * 1000L)
        val sdf = SimpleDateFormat("EEEE, d'${date.getDayOfMonthSuffix(date.day)}' MMMM", Locale.getDefault())
        sdf.format(date)
    } catch (e: Exception) {
        "Invalid Date"
    }
}

fun Date.getDayOfMonthSuffix(n: Int): String {
    return when {
        n in 11..13 -> "th"
        n % 10 == 1 -> "st"
        n % 10 == 2 -> "nd"
        n % 10 == 3 -> "rd"
        else -> "th"
    }
}

fun Int?.formatDateAMPM(): String {
    return try {
        val timestamp = this?.toLong() ?: return "Invalid Timestamp"
        val date = Date(timestamp * 1000L)
        val sdf = SimpleDateFormat("h:mm a", Locale.getDefault())
        sdf.format(date)
    } catch (e: Exception) {
        "Invalid DateTime"
    }
}