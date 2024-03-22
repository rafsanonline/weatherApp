package com.mdrafsanbiswas.weatherapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mdrafsanbiswas.weatherapp.ui.components.ProgressBarHandler
import com.mdrafsanbiswas.weatherapp.ui.main.MainViewModel
import kotlinx.coroutines.launch

@Composable
fun WeatherHomeScreen(
    viewModel: MainViewModel
) {

    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
            .padding(start = 16.dp, top = 20.dp, end = 16.dp, bottom = 16.dp)
    ) {
        ProgressBarHandler(show = viewModel.showProgressBar)
        SearchCard(
            viewModel,
            onSearchClick = {
                coroutineScope.launch {
                    viewModel.fetchWeatherDataByCity(it)
                }
            }
        )

        if (viewModel.weatherData != null) {
            Spacer(modifier = Modifier.height(45.dp))
            LocationInfo(viewModel)
            Spacer(modifier = Modifier.height(10.dp))
            TemperatureCard(viewModel)
            OtherDetails(viewModel)
        }
    }
}


