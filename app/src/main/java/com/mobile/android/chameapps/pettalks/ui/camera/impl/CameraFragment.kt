package com.mobile.android.chameapps.pettalks.ui.camera.impl

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.mobile.android.chameapps.pettalks.R
import com.mobile.android.chameapps.pettalks.app.MyApplication
import com.mobile.android.chameapps.pettalks.mvp.MvpAppCompatFragment
import com.mobile.android.chameapps.pettalks.ui.camera.CameraContract
import javax.inject.Singleton

/**
 * Created by Natallia Zhabitskaya on 10/26/2019.
 */

class CameraFragment : MvpAppCompatFragment(), CameraContract.View {

    @InjectPresenter
    lateinit var presenter: CameraPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (activity!!.application as MyApplication)
            .getAppComponent(activity!!).inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)
        return view
    }

    @ProvidePresenter
    fun providePresenter(): CameraPresenter {
        return (activity!!.application as MyApplication).getAppComponent(activity!!).cameraPresenter
    }

    companion object {

        @Singleton
        fun newInstance(): CameraFragment {
            return CameraFragment()
        }
    }
}