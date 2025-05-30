package com.inkubasi.hirehub.presentation.applicant.dashboard.profile.menu.edit

import androidx.lifecycle.*
import com.inkubasi.hirehub.coreapp.data.source.Resource
import com.inkubasi.hirehub.coreapp.domain.model.User
import com.inkubasi.hirehub.coreapp.domain.usecase.NewHirehubUseCase
import com.inkubasi.hirehub.coreapp.ui.UserPreference
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class EditProfileViewModel(private val hireHubUseCase: NewHirehubUseCase, private val userPreference: UserPreference): ViewModel() {

    private val _stateSuccess = MutableLiveData<Unit>()
    val stateSuccess : LiveData<Unit> = _stateSuccess

    private val _stateError = MutableLiveData<Unit>()
    val stateError: LiveData<Unit> = _stateError

    private val _stateLoading = MutableLiveData<Boolean>()
    val stateLoading : LiveData<Boolean> = _stateLoading

    fun setProfileApplicant(
        token: String, name: String, email: String, dateOfBirth: String, language: List<String>, field: String,
        skills: List<String>, salaryMin: Int, location: String,
        institution: String, degree: String, phone: String, summary: String ){
        hireHubUseCase.editProfileApplicant(token, name, email, dateOfBirth, language, field,
            skills, salaryMin, location, institution, degree, phone, summary).onEach { result ->
            when(result){
                is Resource.Loading -> {
                    _stateLoading.postValue(true)
                }
                is Resource.Error -> {
                    _stateLoading.postValue(false)
                    _stateError.postValue(Unit)
                }
                is Resource.Success -> {
                    _stateLoading.postValue(false)
                    _stateSuccess.postValue(Unit)
                }
            }
        }.launchIn(viewModelScope)

    }

    fun getUser(): LiveData<User> {
        return userPreference.getUser().asLiveData()
    }


}