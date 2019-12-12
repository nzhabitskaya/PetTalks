package com.mobile.android.chameapps.pettalks.application

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

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