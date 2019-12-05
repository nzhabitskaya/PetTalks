package com.mobile.android.chameapps.pettalks.ui.camera.di

import com.mobile.android.chameapps.pettalks.ui.camera.impl.CameraPresenter
import dagger.Module
import dagger.Provides

@Module
class CameraModule {

    @Provides
    fun provideCameraPresenter(): CameraPresenter {
        return CameraPresenter()
    }
}
