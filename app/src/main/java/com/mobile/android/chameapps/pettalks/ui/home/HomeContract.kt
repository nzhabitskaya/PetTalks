package com.mobile.android.chameapps.pettalks.ui.home

import com.arellomobile.mvp.MvpView

/**
 * Created by Natallia Zhabitskaya on 10/26/2019.
 */

interface HomeContract {

    interface View : MvpView {

        fun displayTranslation(text: String)
    }

    interface Presenter {

        fun loadLastTranslation()
    }
}