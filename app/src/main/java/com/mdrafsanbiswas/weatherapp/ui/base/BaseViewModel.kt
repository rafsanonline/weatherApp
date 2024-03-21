package com.mdrafsanbiswas.weatherapp.ui.base

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {
    var showProgressBar by mutableStateOf(true)
}

