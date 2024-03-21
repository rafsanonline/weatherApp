package com.mdrafsanbiswas.weatherapp.repo.weather
import com.mdrafsanbiswas.weatherapp.BuildConfig
import com.mdrafsanbiswas.weatherapp.network.AppNetworkState
import com.mdrafsanbiswas.weatherapp.network.IApiService
import com.mdrafsanbiswas.weatherapp.network.NetworkCallHandler
import com.mdrafsanbiswas.weatherapp.network.convertData
import com.mdrafsanbiswas.weatherapp.network.data.weather_data.WeatherDataResponse
import com.mdrafsanbiswas.weatherapp.repo.BaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    override var apiService: IApiService
) : BaseRepository(), IWeatherRepository {
    override suspend fun fetchWeatherData(
        lat: String,
        lon: String
    ): Flow<AppNetworkState<WeatherDataResponse>> {
        return NetworkCallHandler.handleNetworkCall {
            apiService.getRequest("data/2.5/weather", mapOf(
                "appid" to BuildConfig.API_KEY,
                "lat" to lat,
                "lon" to lon
            )).convertData(WeatherDataResponse::class) as WeatherDataResponse
        }
    }

    override suspend fun fetchWeatherDataByCity(city: String): Flow<AppNetworkState<WeatherDataResponse>> {
        return NetworkCallHandler.handleNetworkCall {
            apiService.getRequest("data/2.5/weather", mapOf(
                "appid" to BuildConfig.API_KEY,
                "q" to city
            )).convertData(WeatherDataResponse::class) as WeatherDataResponse
        }
    }

}