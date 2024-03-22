package com.mdrafsanbiswas.weatherapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material.icons.rounded.WaterDrop
import androidx.compose.material.icons.rounded.WbCloudy
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mdrafsanbiswas.weatherapp.R
import com.mdrafsanbiswas.weatherapp.ui.main.MainViewModel
import com.mdrafsanbiswas.weatherapp.ui.theme.Snow
import com.mdrafsanbiswas.weatherapp.ui.theme.Violet
import com.mdrafsanbiswas.weatherapp.ui.theme.poppins
import com.mdrafsanbiswas.weatherapp.util.formatDateAMPM

@Composable
fun OtherDetails(viewModel: MainViewModel) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = viewModel.weatherData?.weather?.get(0)?.main ?: "",
            fontFamily = poppins,
            fontWeight = FontWeight.Normal,
            color = Violet,
            fontSize = 17.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(30.dp))
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

        Spacer(modifier = Modifier.height(27.dp))

        Row(modifier = Modifier.align(Alignment.End)) {
            Box(modifier = Modifier
                .size(40.dp)
                .background(Snow.copy(.4f), RoundedCornerShape(100))
                .clickable {
                    viewModel.initSearchValue = ""
                    viewModel.updateLocation(viewModel.locationData)
                }) {
                Icon(
                    Icons.Rounded.Refresh,
                    contentDescription = "",
                    tint = Violet,
                    modifier = Modifier
                        .padding(9.dp)
                        .align(Alignment.Center)

                )
            }

            Spacer(modifier = Modifier.width(15.dp))
        }
    }
}