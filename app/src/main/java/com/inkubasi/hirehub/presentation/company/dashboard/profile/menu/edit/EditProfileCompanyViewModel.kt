package com.inkubasi.hirehub.presentation.company.dashboard.profile.menu.edit

import androidx.lifecycle.*
import com.inkubasi.hirehub.coreapp.data.source.Resource
import com.inkubasi.hirehub.coreapp.domain.model.User
import com.inkubasi.hirehub.coreapp.domain.usecase.NewHirehubUseCase
import com.inkubasi.hirehub.coreapp.ui.UserPreference
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class EditProfileCompanyViewModel(
    private val hireHubUseCase: NewHirehubUseCase,
    private val userPreference: UserPreference
): ViewModel()  {

    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState : LiveData<Boolean> = _loadingState

    private val _successEdit = MutableLiveData<Unit>()
    val successEdit : LiveData<Unit> = _successEdit

    fun setProfileCompany(token: String,name: String, location: String, office: String, webUrl: String, industry: String,
                          employee: String, description: String){
        hireHubUseCase.editProfileCompany(token, name, location, office, webUrl, industry, employee, description).onEach {
                result ->
            when(result){
                is Resource.Loading -> {
                    _loadingState.value = true
                }
                is Resource.Error -> {
                    _loadingState.value = false
                }
                is Resource.Success -> {
                    _loadingState.value = false
                    _successEdit.postValue(Unit)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getUser(): LiveData<User> {
        return userPreference.getUser().asLiveData()
    }


}