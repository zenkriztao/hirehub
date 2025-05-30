package com.inkubasi.hirehub.presentation.company.dashboard.profile.menu.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inkubasi.hirehub.coreapp.ui.UserPreference
import kotlinx.coroutines.launch

class SettingCompanyViewModel (
    private val userPreference: UserPreference
) : ViewModel(){

    fun logout() {
        viewModelScope.launch {
            userPreference.logout()
        }
    }

}

