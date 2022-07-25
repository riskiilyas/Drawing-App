package com.keecoding.drawingapp

import android.app.Application
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        SharedPref.init(this)
    }
}