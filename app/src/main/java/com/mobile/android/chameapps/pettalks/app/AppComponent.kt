package com.mobile.android.chameapps.pettalks.app

import com.mobile.android.chameapps.pettalks.ui.home.di.HomeModule
import com.mobile.android.chameapps.pettalks.ui.home.impl.HomeFragment
import com.mobile.android.chameapps.pettalks.ui.home.impl.HomePresenter
import dagger.Component
import javax.inject.Singleton

/**
 * Created by n.zhabitskaya on 9/28/18.
 */

@Singleton
@Component(modules = [ApplicationModule::class, HomeModule::class])
interface AppComponent {

    val application: MyApplication

    val homePresenter: HomePresenter

    fun inject(fragment: HomeFragment)
}
