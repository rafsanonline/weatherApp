package com.mdrafsanbiswas.weatherapp.network

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

object NetworkCallHandler {
    suspend fun <T : Any> handleNetworkCall(callback: suspend () -> T): Flow<AppNetworkState<T>> =
        flow {
            emit(AppNetworkState.Loading)

            try {
                val networkCall = AppNetworkState.Data(callback())

                emit(networkCall)
            } catch (e: Exception) {
                e.printStackTrace()

                emit(e.resolveError())
            }
        }
}