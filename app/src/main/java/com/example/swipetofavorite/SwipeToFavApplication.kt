package com.example.swipetofavorite

import android.app.Application
import com.example.swipetofavorite.di.ApplicationComponent
import com.example.swipetofavorite.di.DaggerApplicationComponent

class SwipeToFavApplication : Application() {

    lateinit var  applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        applicationComponent=DaggerApplicationComponent.factory().create(this)
    }
}