package com.mobile.android.chameapps.pettalks.main

import com.mobile.android.chameapps.pettalks.mvp.BaseContract

class MainContract {

    interface View: BaseContract.View {
        fun openCameraScreen()

        fun openDemoScreen()

        fun openProfileScreen()

        fun hideMenu()
    }

    interface Presenter: BaseContract.Presenter<View> {

        fun registerToggleListeners()

        fun updateDemoToggle(value: Boolean)

        fun updateCcToggle(value: Boolean)

        fun updateVoiceToggle(value: Boolean)

        fun updateTrainingModeToggle(value: Boolean)

        fun startHideMenuCounter()
    }
}