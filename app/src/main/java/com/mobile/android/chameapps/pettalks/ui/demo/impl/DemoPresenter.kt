package com.mobile.android.chameapps.pettalks.ui.demo.impl

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.mobile.android.chameapps.pettalks.ui.demo.DemoContract

@InjectViewState
class DemoPresenter() : MvpPresenter<DemoContract.View>(),
    DemoContract.Presenter {

}
