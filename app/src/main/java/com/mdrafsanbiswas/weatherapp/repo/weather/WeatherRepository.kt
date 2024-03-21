package com.mdrafsanbiswas.weatherapp.repo.weather
import com.mdrafsanbiswas.weatherapp.BuildConfig
import com.mdrafsanbiswas.weatherapp.network.IApiService
import com.mdrafsanbiswas.weatherapp.repo.BaseRepository
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    override var apiService: IApiService
) : BaseRepository(), IWeatherRepository {

}