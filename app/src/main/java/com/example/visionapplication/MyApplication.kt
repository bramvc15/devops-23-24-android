package com.example.visionapplication

import android.app.Application
import com.example.visionapplication.data.AppContainer
import com.example.visionapplication.data.DefaultAppContainer

/**
 * My application
 *
 * @constructor Create empty My application
 */
class MyApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(context = applicationContext)
    }
}