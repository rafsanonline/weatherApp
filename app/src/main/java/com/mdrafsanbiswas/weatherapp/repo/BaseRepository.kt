package com.mdrafsanbiswas.weatherapp.repo

import com.mdrafsanbiswas.weatherapp.network.AppNetworkState
import com.mdrafsanbiswas.weatherapp.network.IApiService
import com.mdrafsanbiswas.weatherapp.network.NetworkErrorExceptions
import com.mdrafsanbiswas.weatherapp.network.data.BaseResponse
import com.mdrafsanbiswas.weatherapp.network.resolveError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


abstract class BaseRepository : IBaseRepository {
    abstract var apiService: IApiService

    protected suspend fun <T : Any> handleNetworkCall(callback: suspend () -> T): Flow<AppNetworkState<T>> =
        flow {
            emit(AppNetworkState.Loading)

            try {
                val networkCall = AppNetworkState.Data(callback())
                val baseData = networkCall.data as BaseResponse
                when (baseData.status) {
                    "ok" -> {
                        emit(networkCall)
                    }
                    else -> {
                        throw NetworkErrorExceptions(
                            errorCode = baseData.code ?: -1,
                            errorBody = baseData,
                            errorMessage = baseData.message ?: "Something went wrong!",
                            unauthorized = baseData.code == 401
                        )
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()

                emit(e.resolveError())
            }
        }

}