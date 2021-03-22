package com.gtreb.healthydog.common.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel

abstract class CustomViewModel(application: Application) : AndroidViewModel(application) {
    abstract fun load()
}