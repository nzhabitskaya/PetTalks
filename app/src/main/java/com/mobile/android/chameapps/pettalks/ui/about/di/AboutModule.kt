package com.mobile.android.chameapps.pettalks.ui.about.di

import com.mobile.android.chameapps.pettalks.ui.about.impl.AboutPresenter
import com.mobile.android.chameapps.pettalks.ui.demo.impl.DemoPresenter
import dagger.Module
import dagger.Provides

@Module
class AboutModule {

    @Provides
    fun provideAboutPresenter(): AboutPresenter {
        return AboutPresenter()
    }
}
