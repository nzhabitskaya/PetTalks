package com.mobile.android.chameapps.pettalks.application

import android.app.Application
import android.content.Context

class MyApplication : Application() {
    private var component: AppComponent? = null
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    fun getAppComponent(context: Context): AppComponent? {
        val app = context.applicationContext as MyApplication
        if (app.component == null) {
            app.component = DaggerAppComponent.builder()
                .applicationModule(ApplicationModule(instance!!))
                .build()
        }
        return app.component
    }

    companion object {
        var instance: MyApplication? = null
            private set
    }
}