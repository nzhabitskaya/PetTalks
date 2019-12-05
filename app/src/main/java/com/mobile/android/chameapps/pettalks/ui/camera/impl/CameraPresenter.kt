package com.mobile.android.chameapps.pettalks.ui.camera.impl

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.mobile.android.chameapps.pettalks.ui.camera.CameraContract

@InjectViewState
class CameraPresenter() : MvpPresenter<CameraContract.View>(),
    CameraContract.Presenter {

    override fun loadLastTranslation() {
    }
}
