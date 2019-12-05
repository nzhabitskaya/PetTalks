package com.mobile.android.chameapps.pettalks.ui.demo.di

import com.mobile.android.chameapps.pettalks.ui.demo.impl.DemoPresenter
import dagger.Module
import dagger.Provides

@Module
class DemoModule {

    @Provides
    fun provideDemoPresenter(): DemoPresenter {
        return DemoPresenter()
    }
}
