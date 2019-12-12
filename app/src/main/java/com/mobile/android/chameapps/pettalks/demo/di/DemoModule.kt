package com.mobile.android.chameapps.pettalks.demo.di

import com.mobile.android.chameapps.pettalks.demo.DemoContract
import com.mobile.android.chameapps.pettalks.demo.impl.DemoPresenter
import com.mobile.android.chameapps.pettalks.main.MainContract
import com.mobile.android.chameapps.pettalks.main.impl.MainModel
import com.mobile.android.chameapps.pettalks.main.impl.MainPresenter
import dagger.Module
import dagger.Provides

@Module
class DemoModule {

    @Provides
    fun providePresenter(model: MainModel): DemoContract.Presenter {
        return DemoPresenter(model)
    }
}