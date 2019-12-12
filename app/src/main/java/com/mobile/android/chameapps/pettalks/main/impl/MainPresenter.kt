package com.mobile.android.chameapps.pettalks.main.impl

import com.mobile.android.chameapps.pettalks.main.MainContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainPresenter(model: MainModel): MainContract.Presenter {

    private val subscriptions = CompositeDisposable()
    private lateinit var view: MainContract.View
    private val model: MainModel

    init {
        this.model = model
    }

    override fun subscribe() {

    }

    override fun unsubscribe() {
        subscriptions.clear()
    }

    override fun attach(view: MainContract.View) {
        this.view = view
    }

    override fun registerToggleListeners() {
        val subscribe1 = model.demoToggle
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ isDemo ->
                if(isDemo) {
                    view.openDemoScreen()
                } else {
                    view.openCameraScreen()
                }
            })

        subscriptions.add(subscribe1)

        val subscribe2 = model.openProfile
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ view.openProfileScreen() })

        subscriptions.add(subscribe2)

        val subscribe3 = model.openDemo
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ view.openDemoScreen() })

        subscriptions.add(subscribe3)
    }

    override fun updateDemoToggle(value: Boolean) {
        model.demoToggle.onNext(value)
    }

    override fun updateCcToggle(value: Boolean) {
        model.ccToggle.onNext(value)
    }

    override fun updateVoiceToggle(value: Boolean) {
        model.voiceToggle.onNext(value)
    }

    override fun updateTrainingModeToggle(value: Boolean) {
        model.trainingModeToggle.onNext(value)
    }
}