package com.mobile.android.chameapps.pettalks.main.di

import com.mobile.android.chameapps.pettalks.main.MainContract
import com.mobile.android.chameapps.pettalks.main.impl.MainModel
import com.mobile.android.chameapps.pettalks.main.impl.MainPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MainModule {

    @Provides
    @Singleton
    fun provideModel(): MainModel {
        return MainModel()
    }

    @Provides
    fun providePresenter(model: MainModel): MainContract.Presenter {
        return MainPresenter(model)
    }
}