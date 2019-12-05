package com.mobile.android.chameapps.pettalks.app

import com.mobile.android.chameapps.pettalks.ui.about.di.AboutModule
import com.mobile.android.chameapps.pettalks.ui.about.impl.AboutFragment
import com.mobile.android.chameapps.pettalks.ui.about.impl.AboutPresenter
import com.mobile.android.chameapps.pettalks.ui.camera.di.CameraModule
import com.mobile.android.chameapps.pettalks.ui.camera.impl.CameraFragment
import com.mobile.android.chameapps.pettalks.ui.camera.impl.CameraPresenter
import com.mobile.android.chameapps.pettalks.ui.demo.di.DemoModule
import com.mobile.android.chameapps.pettalks.ui.demo.impl.DemoFragment
import com.mobile.android.chameapps.pettalks.ui.demo.impl.DemoPresenter
import dagger.Component
import javax.inject.Singleton

/**
 * Created by n.zhabitskaya on 9/28/18.
 */

@Singleton
@Component(modules = [ApplicationModule::class, DemoModule::class, CameraModule::class, AboutModule::class])
interface AppComponent {

    val application: MyApplication

    val demoPresenter: DemoPresenter

    val cameraPresenter: CameraPresenter

    val aboutPresenter: AboutPresenter

    fun inject(fragment: DemoFragment)

    fun inject(fragment: CameraFragment)

    fun inject(fragment: AboutFragment)
}
