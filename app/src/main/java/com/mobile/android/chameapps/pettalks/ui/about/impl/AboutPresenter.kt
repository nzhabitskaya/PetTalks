package com.mobile.android.chameapps.pettalks.ui.about.impl

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.mobile.android.chameapps.pettalks.ui.about.AboutContract

@InjectViewState
class AboutPresenter() : MvpPresenter<AboutContract.View>(),
    AboutContract.Presenter {

    override fun loadLastTranslation() {
    }
}
