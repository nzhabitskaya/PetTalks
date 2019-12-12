package com.mobile.android.chameapps.pettalks.application

import com.mobile.android.chameapps.pettalks.demo.DemoContract
import com.mobile.android.chameapps.pettalks.demo.di.DemoModule
import com.mobile.android.chameapps.pettalks.demo.impl.DemoFragment
import com.mobile.android.chameapps.pettalks.main.MainContract
import com.mobile.android.chameapps.pettalks.main.di.MainModule
import com.mobile.android.chameapps.pettalks.main.impl.MainActivity
import com.mobile.android.chameapps.pettalks.profile.ProfileContract
import com.mobile.android.chameapps.pettalks.profile.di.ProfileModule
import com.mobile.android.chameapps.pettalks.profile.impl.ProfileFragment
import dagger.Component
import javax.inject.Singleton

/**
 * Created by n.zhabitskaya on 9/28/18.
 */

@Singleton
@Component(modules = [ApplicationModule::class, MainModule::class, DemoModule::class, ProfileModule::class])
interface AppComponent {

    val application: MyApplication?

    val mainPresenter: MainContract.Presenter

    val demoPresenter: DemoContract.Presenter

    val profilePresenter: ProfileContract.Presenter

    fun inject(activity: MainActivity?)

    fun inject(fragment: DemoFragment?)

    fun inject(fragment: ProfileFragment?)
}