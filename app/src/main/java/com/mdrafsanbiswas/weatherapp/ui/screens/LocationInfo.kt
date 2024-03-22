package com.mdrafsanbiswas.weatherapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.sp
import com.mdrafsanbiswas.weatherapp.ui.main.MainViewModel
import com.mdrafsanbiswas.weatherapp.ui.theme.Violet
import com.mdrafsanbiswas.weatherapp.ui.theme.poppins
import com.mdrafsanbiswas.weatherapp.util.formatDate

@Composable
fun LocationInfo(viewModel: MainViewModel) {
    val data = viewModel.weatherData
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = data?.dt?.formatDate() ?: "",
            fontFamily = poppins,
            fontSize = 20.sp,
            color = Violet,
            letterSpacing = TextUnit(0.8F, TextUnitType.Sp),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Text(
            text = data?.name ?: "",
            fontFamily = poppins,
            fontSize = 16.sp,
            color = Violet,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}