package com.example.sampleapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GolfApp : Application() {

    companion object {
        const val API_KEY = "Key KUEN6LOJ7HI5ZWBEXFHPJ4BFQQ"
    }

}