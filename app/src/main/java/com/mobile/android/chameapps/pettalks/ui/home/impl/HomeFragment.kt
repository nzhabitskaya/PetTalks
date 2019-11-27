package com.mobile.android.chameapps.pettalks.ui.home.impl

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.mobile.android.chameapps.pettalks.R
import com.mobile.android.chameapps.pettalks.app.MyApplication
import com.mobile.android.chameapps.pettalks.mvp.MvpAppCompatFragment
import com.mobile.android.chameapps.pettalks.ui.home.HomeContract

import javax.inject.Singleton

/**
 * Created by Natallia Zhabitskaya on 10/26/2019.
 */

class HomeFragment : MvpAppCompatFragment(), HomeContract.View {

    @InjectPresenter
    lateinit var presenter: HomePresenter

    private var mTextView: TextView? = null

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
        mTextView = view.findViewById(R.id.text_view) as TextView
        return view
    }

    override fun displayTranslation(text: String) {
        var text = text
        if (text.length > 0) {
            text = text[0].toString().toUpperCase() + text.subSequence(1, text.length)
        }
        mTextView!!.text = text
    }

    override fun onResume() {
        super.onResume()
        presenter!!.loadLastTranslation()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        startSpeechRecognizer()
    }

    private fun startSpeechRecognizer() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        //intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.main_question));
        //startActivityForResult(intent, NavigationActivity.REQUEST_SPEECH_RECOGNIZER);
    }

    @ProvidePresenter
    fun providePresenter(): HomePresenter {
        return (activity!!.application as MyApplication).getAppComponent(activity!!).homePresenter
    }

    companion object {

        @Singleton
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }
}