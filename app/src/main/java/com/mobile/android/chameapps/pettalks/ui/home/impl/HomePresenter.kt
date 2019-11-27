package com.mobile.android.chameapps.pettalks.ui.home.impl

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.mobile.android.chameapps.pettalks.ui.home.HomeContract

@InjectViewState
class HomePresenter() : MvpPresenter<HomeContract.View>(),
    HomeContract.Presenter {

    override fun loadLastTranslation() {
    }
}
