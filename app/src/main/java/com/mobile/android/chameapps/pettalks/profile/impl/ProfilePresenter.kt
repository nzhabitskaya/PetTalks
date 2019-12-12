package com.mobile.android.chameapps.pettalks.profile.impl

import com.mobile.android.chameapps.pettalks.main.impl.MainModel
import com.mobile.android.chameapps.pettalks.profile.ProfileContract
import io.reactivex.disposables.CompositeDisposable

class ProfilePresenter(model: MainModel) : ProfileContract.Presenter {

    private val subscriptions = CompositeDisposable()
    private lateinit var view: ProfileContract.View
    private val model: MainModel

    init {
        this.model = model
    }

    override fun subscribe() {

    }

    override fun unsubscribe() {
        subscriptions.clear()
    }

    override fun attach(view: ProfileContract.View) {
        this.view = view
    }

    override fun openDemo() {
        model.openDemo.onNext(Unit)
    }
}