package com.mobile.android.chameapps.pettalks.profile.impl

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mobile.android.chameapps.pettalks.R
import com.mobile.android.chameapps.pettalks.application.MyApplication
import com.mobile.android.chameapps.pettalks.demo.DemoContract
import com.mobile.android.chameapps.pettalks.profile.ProfileContract
import kotlinx.android.synthetic.main.activity_form_signup_image.view.*
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Created by Natallia Zhabitskaya on 10/26/2019.
 */

class ProfileFragment : Fragment(), ProfileContract.View {

    @Inject
    lateinit var presenter: ProfileContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependency()
        presenter.attach(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.activity_form_signup_image, container, false)
        view.bt_submit.setOnClickListener{ presenter.openDemo() }
        return view
    }

    private fun injectDependency() {
        (activity!!.application as MyApplication).getAppComponent(context!!)!!.inject(this)
    }

    companion object {

        @Singleton
        fun newInstance(): ProfileFragment {
            return ProfileFragment()
        }
    }
}