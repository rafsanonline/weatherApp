package com.mdrafsanbiswas.weatherapp.util.network_connectivity

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.compose.ui.platform.LocalContext
import com.mdrafsanbiswas.weatherapp.util.currentConnectivityState
import com.mdrafsanbiswas.weatherapp.util.observeConnectivityAsFlow


@Composable
fun connectivityState(): State<ConnectionState> {
    val context = LocalContext.current

    // Creates a State<ConnectionState> with current connectivity state as initial value
    return produceState(initialValue = context.currentConnectivityState) {
        // In a coroutine, can make suspend calls
        context.observeConnectivityAsFlow().collect {
            value = it
            Log.i("data-->", "kadfjkd")
        }
    }
}