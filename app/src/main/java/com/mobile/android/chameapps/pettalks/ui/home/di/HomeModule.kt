package com.mobile.android.chameapps.pettalks.ui.home.di

import com.mobile.android.chameapps.pettalks.ui.home.impl.HomePresenter
import dagger.Module
import dagger.Provides

@Module
class HomeModule {

    @Provides
    fun provideHomePresenter(): HomePresenter {
        return HomePresenter()
    }
}
