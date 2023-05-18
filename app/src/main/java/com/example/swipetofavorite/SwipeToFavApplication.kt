package com.example.swipetofavorite

import android.app.Application
import android.content.Context
import com.example.swipetofavorite.di.ApplicationComponent
import com.example.swipetofavorite.di.DaggerApplicationComponent

class SwipeToFavApplication : Application() {

    lateinit var  applicationComponent: ApplicationComponent
    companion object {
        var ctx: Context? = null
    }
    override fun onCreate() {
        super.onCreate()
        ctx = applicationContext

        applicationComponent=DaggerApplicationComponent.factory().create(this)
    }
}