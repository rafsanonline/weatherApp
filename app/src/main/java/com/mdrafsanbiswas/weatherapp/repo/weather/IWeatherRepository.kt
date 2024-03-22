package com.mdrafsanbiswas.weatherapp.repo.weather

import com.mdrafsanbiswas.weatherapp.network.AppNetworkState
import com.mdrafsanbiswas.weatherapp.network.data.weather_data.WeatherDataResponse
import com.mdrafsanbiswas.weatherapp.repo.IBaseRepository
import kotlinx.coroutines.flow.Flow


interface IWeatherRepository : IBaseRepository {
    suspend fun fetchWeatherData(
        lat: String,
        lon: String
    ): Flow<AppNetworkState<WeatherDataResponse>>

    suspend fun fetchWeatherDataByCity(city: String): Flow<AppNetworkState<WeatherDataResponse>>
}
