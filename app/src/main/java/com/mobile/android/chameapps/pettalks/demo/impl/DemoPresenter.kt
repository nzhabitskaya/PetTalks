package com.mobile.android.chameapps.pettalks.demo.impl

import com.mobile.android.chameapps.pettalks.demo.DemoContract
import com.mobile.android.chameapps.pettalks.main.impl.MainModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class DemoPresenter(model: MainModel): DemoContract.Presenter {

    private val subscriptions = CompositeDisposable()
    private lateinit var view: DemoContract.View
    private val model: MainModel

    init {
        this.model = model
    }

    override fun subscribe() {

    }

    override fun unsubscribe() {
        subscriptions.clear()
    }

    override fun attach(view: DemoContract.View) {
        this.view = view
    }

    override fun registerToggleListeners() {
        val subscribe1 = model.ccToggle
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ view.updateCcValue(it) })

        subscriptions.add(subscribe1)

        val subscribe2 = model.voiceToggle
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ view.updateVoiceValue(it) })

        subscriptions.add(subscribe2)

        val subscribe3 = model.trainingModeToggle
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ view.updateTrainingModeValue(it) })

        subscriptions.add(subscribe3)
    }

    override fun openProfile() {
        model.openProfile.onNext(Unit)
    }
}