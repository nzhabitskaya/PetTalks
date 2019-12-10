package com.mobile.android.chameapps.pettalks.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mobile.android.chameapps.pettalks.R
import javax.inject.Singleton


/**
 * Created by Natallia Zhabitskaya on 10/26/2019.
 */

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.activity_form_signup_image, container, false)
        return view
    }

    companion object {

        @Singleton
        fun newInstance(): ProfileFragment {
            return ProfileFragment()
        }
    }
}