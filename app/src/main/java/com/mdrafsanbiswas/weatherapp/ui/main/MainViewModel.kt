package com.mdrafsanbiswas.weatherapp.ui.main

import android.location.Location
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mdrafsanbiswas.weatherapp.network.AppNetworkState
import com.mdrafsanbiswas.weatherapp.network.data.weather_data.WeatherDataResponse
import com.mdrafsanbiswas.weatherapp.repo.weather.IWeatherRepository
import com.mdrafsanbiswas.weatherapp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val iWeatherRepository: IWeatherRepository
) : BaseViewModel() {

    var weatherData by mutableStateOf<WeatherDataResponse?>(null)
    var locationData by mutableStateOf<Location?>(null)
    var initSearchValue by mutableStateOf("")
    var errorMessage = MutableLiveData("")
    suspend fun fetchWeatherData(lat: String, lon: String) =
        iWeatherRepository.fetchWeatherData(lat, lon).collectLatest {
            when (it) {
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

    suspend fun fetchWeatherDataByCity(city: String) =
        iWeatherRepository.fetchWeatherDataByCity(city).collectLatest {
            when (it) {
                is AppNetworkState.Loading -> {
                    showProgressBar = true
                }

                is AppNetworkState.Data -> {
                    showProgressBar = false
                    weatherData = it.data
                }

                is AppNetworkState.Error -> {
                    errorMessage.value = it.errorBody?.message.toString()
                    showProgressBar = false
                }
            }
        }

    fun updateLocation(lo: Location?) {
        initSearchValue = ""
        locationData = lo
        viewModelScope.launch {
            fetchWeatherData(locationData?.latitude.toString(), locationData?.longitude.toString())
        }
    }
}