package com.mobile.android.chameapps.pettalks.profile.impl

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.mobile.android.chameapps.pettalks.R
import com.mobile.android.chameapps.pettalks.application.MyApplication
import com.mobile.android.chameapps.pettalks.profile.ProfileContract
import kotlinx.android.synthetic.main.profile_layout.view.*
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

        val view = inflater.inflate(R.layout.profile_layout, container, false)
        view.bt_submit.setOnClickListener { presenter.openDemo() }
        view.select_language.setOnClickListener { showChoiceDialog(it as EditText, R.array.langs) }
        view.select_type.setOnClickListener { showChoiceDialog(it as EditText, R.array.types) }
        view.select_breed.setOnClickListener { showChoiceDialog(it as EditText, R.array.breeds) }
        return view
    }

    private fun injectDependency() {
        (activity!!.application as MyApplication).getAppComponent(context!!)!!.inject(this)
    }

    private fun showChoiceDialog(bt: EditText, resId: Int) {
        val array = resources.getStringArray(resId)
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setCancelable(true)
        builder.setSingleChoiceItems(
            array,
            0,
            { dialog, which ->
                dialog.dismiss()
                bt.setText(array.get(which))
            })
        builder.show()
    }

    companion object {

        @Singleton
        fun newInstance(): ProfileFragment {
            return ProfileFragment()
        }
    }
}