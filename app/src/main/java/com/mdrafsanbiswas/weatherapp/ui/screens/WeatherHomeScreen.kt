package com.mdrafsanbiswas.weatherapp.ui.screens

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.WaterDrop
import androidx.compose.material.icons.rounded.WbCloudy
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mdrafsanbiswas.weatherapp.BuildConfig
import com.mdrafsanbiswas.weatherapp.R
import com.mdrafsanbiswas.weatherapp.ui.components.ProgressBarHandler
import com.mdrafsanbiswas.weatherapp.ui.main.MainViewModel
import com.mdrafsanbiswas.weatherapp.ui.theme.Violet
import com.mdrafsanbiswas.weatherapp.ui.theme.poppins
import com.mdrafsanbiswas.weatherapp.util.formatDate
import com.mdrafsanbiswas.weatherapp.util.formatDateAMPM
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun WeatherHomeScreen(
    viewModel: MainViewModel
) {
    LaunchedEffect(key1 = Unit, block = {
        viewModel.fetchWeatherData("23", "90")
    })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
            .padding(start = 16.dp, top = 20.dp, end = 16.dp, bottom = 16.dp)
    ) {
        SearchCard(
            onSearchClick = {

            }
        )

        if(viewModel.weatherData != null) {
            Spacer(modifier = Modifier.height(45.dp))
            TemperatureDetails(viewModel)
            Spacer(modifier = Modifier.height(10.dp))
            TemperatureCard(viewModel)

            Text(
                text = viewModel.weatherData?.weather?.get(0)?.main?:"",
                fontFamily = poppins,
                fontWeight = FontWeight.Normal,
                color = Violet,
                fontSize = 17.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(30.dp))

            OtherDetails(viewModel)
        } else {
            ProgressBarHandler(viewModel.showProgressBar)
        }
    }
}

@Composable
fun OtherDetails(viewModel: MainViewModel) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painterResource(id = R.drawable.sunrise),
                contentDescription = "",
                tint = Violet,
                modifier = Modifier
                    .size(50.dp)
                    .padding(10.dp)
                    .align(Alignment.CenterVertically)
            )

            Spacer(modifier = Modifier.width(5.dp))

            Text(
                text = stringResource(R.string.sunrise),
                fontFamily = poppins,
                fontWeight = FontWeight.Normal,
                color = Violet,
                fontSize = 14.sp,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = viewModel.weatherData?.sys?.sunrise.formatDateAMPM(),
                fontFamily = poppins,
                fontWeight = FontWeight.Normal,
                color = Violet,
                fontSize = 14.sp,
            )


        }
        Divider(color = Color.Black.copy(.2f), thickness = 0.5.dp)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painterResource(id = R.drawable.sunset),
                contentDescription = "",
                tint = Violet,
                modifier = Modifier
                    .size(50.dp)
                    .padding(10.dp)
                    .align(Alignment.CenterVertically)
            )

            Spacer(modifier = Modifier.width(5.dp))

            Text(
                text = stringResource(R.string.sunset),
                fontFamily = poppins,
                fontWeight = FontWeight.Normal,
                color = Violet,
                fontSize = 14.sp,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = viewModel.weatherData?.sys?.sunset.formatDateAMPM(),
                fontFamily = poppins,
                fontWeight = FontWeight.Normal,
                color = Violet,
                fontSize = 14.sp,
            )


        }
        Divider(color = Color.Black.copy(.2f), thickness = 0.5.dp)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Rounded.WaterDrop,
                contentDescription = "",
                tint = Violet,
                modifier = Modifier
                    .size(50.dp)
                    .padding(12.dp)
                    .align(Alignment.CenterVertically)
            )

            Spacer(modifier = Modifier.width(5.dp))

            Text(
                text = stringResource(R.string.humidity),
                fontFamily = poppins,
                fontWeight = FontWeight.Normal,
                color = Violet,
                fontSize = 14.sp,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = "${viewModel.weatherData?.main?.humidity} %",
                fontFamily = poppins,
                fontWeight = FontWeight.Normal,
                color = Violet,
                fontSize = 14.sp,
            )


        }
        Divider(color = Color.Black.copy(.2f), thickness = 0.5.dp)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Rounded.WbCloudy,
                contentDescription = "",
                tint = Violet,
                modifier = Modifier
                    .size(50.dp)
                    .padding(13.dp)
                    .align(Alignment.CenterVertically)
            )

            Spacer(modifier = Modifier.width(5.dp))

            Text(
                text = stringResource(R.string.clouds),
                fontFamily = poppins,
                fontWeight = FontWeight.Normal,
                color = Violet,
                fontSize = 14.sp,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = "${viewModel.weatherData?.clouds?.all} %",
                fontFamily = poppins,
                fontWeight = FontWeight.Normal,
                color = Violet,
                fontSize = 14.sp,
            )


        }
    }
}

@Composable
fun TemperatureDetails(viewModel: MainViewModel) {
    val data = viewModel.weatherData
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = data?.dt?.formatDate()?:"",
            fontFamily = poppins,
            fontSize = 20.sp,
            color = Violet,
            letterSpacing = TextUnit(0.8F, TextUnitType.Sp),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Text(
            text = data?.name?:"",
            fontFamily = poppins,
            fontSize = 16.sp,
            color = Violet,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun TemperatureCard(viewModel: MainViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp), horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "${viewModel.weatherData?.main?.temp?.toInt()?:""}°",
            fontFamily = poppins,
            fontWeight = FontWeight.Light,
            color = Violet,
            fontSize = 100.sp,
        )

        CoilImage(imageModel = "${BuildConfig.IMAGE_URL}/img/wn/${viewModel.weatherData!!.weather[0].icon}@4x.png", modifier = Modifier
            .size(100.dp)
            .align(Alignment.Top),
        )
    }
}
