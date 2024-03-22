package com.mdrafsanbiswas.weatherapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mdrafsanbiswas.weatherapp.BuildConfig
import com.mdrafsanbiswas.weatherapp.ui.main.MainViewModel
import com.mdrafsanbiswas.weatherapp.ui.theme.Violet
import com.mdrafsanbiswas.weatherapp.ui.theme.poppins
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun TemperatureCard(viewModel: MainViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp), horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "${viewModel.weatherData?.main?.temp?.toInt() ?: ""}°",
            fontFamily = poppins,
            fontWeight = FontWeight.Light,
            color = Violet,
            fontSize = 100.sp,
        )

        CoilImage(
            imageModel = "${BuildConfig.IMAGE_URL}/img/wn/${viewModel.weatherData!!.weather[0].icon}@4x.png",
            modifier = Modifier
                .size(100.dp)
                .align(Alignment.Top),
        )
    }
}