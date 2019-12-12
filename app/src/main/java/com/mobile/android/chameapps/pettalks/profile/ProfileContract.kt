package com.mobile.android.chameapps.pettalks.profile

import com.mobile.android.chameapps.pettalks.mvp.BaseContract

class ProfileContract {

    interface View: BaseContract.View

    interface Presenter: BaseContract.Presenter<View> {
        fun openDemo()
    }
}