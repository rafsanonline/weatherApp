package com.mdrafsanbiswas.weatherapp.util.network_connectivity

sealed class ConnectionState {
    object Available : ConnectionState()
    object Unavailable : ConnectionState()
}