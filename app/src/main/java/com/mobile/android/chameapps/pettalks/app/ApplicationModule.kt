package com.mobile.android.chameapps.pettalks.app

import javax.inject.Singleton

import dagger.Module
import dagger.Provides

/**
 * Created by n.zhabitskaya on 9/28/18.
 */

@Module
class ApplicationModule(private val application: MyApplication) {

    @Singleton
    @Provides
    fun provideApplication(): MyApplication {
        return application
    }
}
