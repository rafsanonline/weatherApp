package com.mdrafsanbiswas.weatherapp.network

import android.util.Log
import com.google.gson.GsonBuilder
import com.mdrafsanbiswas.weatherapp.network.data.BaseResponse
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import kotlin.reflect.KClass

sealed class AppNetworkState<out T> {
    object Loading : AppNetworkState<Nothing>()

    data class Error(
        var exception: NetworkErrorExceptions,
        var errorBody: BaseResponse? = null,
        var unauthorized: Boolean = false
    ) : AppNetworkState<Nothing>()

    data class Data<T>(var data: T) : AppNetworkState<T>()
}

open class NetworkErrorExceptions(
    val errorCode: Int = -1,
    val errorMessageRes: Int? = null,
    val errorMessage: String,
    val errorBody: BaseResponse? = null,
    val unauthorized: Boolean = false
) : Exception() {
    override val message: String?
        get() = errorMessage

    companion object {
        fun parseException(exception: HttpException): NetworkErrorExceptions {
            return try {
                val errorBody =
                    exception.response()?.errorBody()
                        ?.convertBody(BaseResponse::class) as BaseResponse

                NetworkErrorExceptions(
                    errorCode = errorBody.code ?: exception.code(),
                    errorMessage = errorBody.status ?: "request failed",
                    errorBody = errorBody,
                    unauthorized = errorBody.code == 401 // unauthorized true if 401
                )
            } catch (_: Exception) {
                NetworkErrorExceptions(
                    errorCode = exception.code(),
                    errorMessage = "unexpected error!!",
                )
            }
        }
    }
}

fun Exception.resolveError(): AppNetworkState.Error {
    Log.d("check_exception", "${this.message}")
    when (this) {
        is SocketTimeoutException -> {
            val exception = NetworkErrorExceptions(errorMessage = "connection error!")
            return AppNetworkState.Error(
                exception = exception,
                errorBody = exception.errorBody,
                unauthorized = exception.unauthorized
            )
        }

        is ConnectException -> {
            val exception = NetworkErrorExceptions(errorMessage = "no internet access!")
            return AppNetworkState.Error(
                exception = exception,
                errorBody = exception.errorBody,
                unauthorized = exception.unauthorized
            )
        }

        is UnknownHostException -> {
            val exception = NetworkErrorExceptions(errorMessage = "host not found!")
            return AppNetworkState.Error(
                exception = exception,
                errorBody = exception.errorBody,
                unauthorized = exception.unauthorized
            )
        }

        is HttpException -> {
            val exception = NetworkErrorExceptions.parseException(this)
            return AppNetworkState.Error(
                exception = exception,
                errorBody = exception.errorBody,
                unauthorized = exception.unauthorized
            )
        }

        is NetworkErrorExceptions -> {
            return AppNetworkState.Error(
                exception = this,
                errorBody = this.errorBody,
                unauthorized = this.unauthorized
            )
        }
    }

    return AppNetworkState.Error(
        exception = NetworkErrorExceptions(errorMessage = this.message.toString()),
        errorBody = null,
        unauthorized = false
    )
}

fun <T : Any> Response<ResponseBody>.convertData(classType: KClass<T>): Any {
    val body = if (this.isSuccessful) {
        this.body()?.string()
    } else throw HttpException(this)

    return GsonBuilder().serializeNulls().create().fromJson(
        body,
        classType.java
    )
}

fun <T : Any> ResponseBody.convertBody(classType: KClass<T>): Any {
    return GsonBuilder().serializeNulls().create().fromJson(
        this.string(),
        classType.java
    )
}

