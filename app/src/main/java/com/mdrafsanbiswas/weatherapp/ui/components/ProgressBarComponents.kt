package com.mdrafsanbiswas.weatherapp.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.mdrafsanbiswas.weatherapp.ui.theme.Violet

@Composable
fun ProgressBarHandler(show: Boolean) {
    if (!show) return

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = Violet,
            modifier = Modifier
                .wrapContentHeight()
                .align(Alignment.Center)
        )
    }
}