package com.example.weater

import android.app.Application
import com.example.weater.dependency.Component
import com.example.weater.dependency.DaggerComponent

class CustomApp : Application() {

    override fun onCreate() {
        super.onCreate()
        injector = DaggerComponent.create()
    }

    companion object {
        var injector: Component? = null
    }
}