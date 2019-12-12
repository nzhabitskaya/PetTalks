package com.mobile.android.chameapps.pettalks.profile.di

import com.mobile.android.chameapps.pettalks.main.impl.MainModel
import com.mobile.android.chameapps.pettalks.profile.ProfileContract
import com.mobile.android.chameapps.pettalks.profile.impl.ProfilePresenter
import dagger.Module
import dagger.Provides

@Module
class ProfileModule {

    @Provides
    fun providePresenter(model: MainModel): ProfileContract.Presenter {
        return ProfilePresenter(model)
    }
}