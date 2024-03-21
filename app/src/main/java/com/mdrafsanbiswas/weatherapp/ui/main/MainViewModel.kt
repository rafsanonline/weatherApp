package com.mdrafsanbiswas.weatherapp.ui.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.mdrafsanbiswas.weatherapp.network.AppNetworkState
import com.mdrafsanbiswas.weatherapp.network.data.weather_data.WeatherDataResponse
import com.mdrafsanbiswas.weatherapp.repo.weather.IWeatherRepository
import com.mdrafsanbiswas.weatherapp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val iWeatherRepository: IWeatherRepository
) : BaseViewModel() {

    var weatherData  by mutableStateOf<WeatherDataResponse?>(null)
    suspend fun fetchWeatherData(lat: String, lon: String) = iWeatherRepository.fetchWeatherData(lat, lon).collectLatest {
        when(it) {
            is AppNetworkState.Loading -> {
                showProgressBar = true
            }

            is AppNetworkState.Data -> {
                showProgressBar = false
                weatherData = it.data
            }
            is AppNetworkState.Error -> {
                showProgressBar = false
            }
        }
    }
}