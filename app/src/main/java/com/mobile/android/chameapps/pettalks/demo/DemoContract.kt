package com.mobile.android.chameapps.pettalks.demo

import com.mobile.android.chameapps.pettalks.mvp.BaseContract

class DemoContract {

    interface View: BaseContract.View {

        fun updateCcValue(value: Boolean)

        fun updateVoiceValue(value: Boolean)

        fun updateTrainingModeValue(value: Boolean)
    }

    interface Presenter: BaseContract.Presenter<View> {
        fun registerToggleListeners()

        fun openProfile()
    }
}