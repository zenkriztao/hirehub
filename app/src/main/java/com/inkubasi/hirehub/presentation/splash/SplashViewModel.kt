package com.inkubasi.hirehub.presentation.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.inkubasi.hirehub.coreapp.domain.model.User
import com.inkubasi.hirehub.coreapp.ui.UserPreference

class SplashViewModel(private val userPreference: UserPreference): ViewModel() {

    fun getUser(): LiveData<User> {
        return userPreference.getUser().asLiveData()
    }

}