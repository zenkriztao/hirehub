package com.inkubasi.hirehub.presentation.applicant.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inkubasi.hirehub.coreapp.data.source.Resource
import com.inkubasi.hirehub.coreapp.data.source.remote.newresponse.SignInResponse
import com.inkubasi.hirehub.coreapp.domain.model.User
import com.inkubasi.hirehub.coreapp.domain.usecase.NewHirehubUseCase
import com.inkubasi.hirehub.coreapp.ui.UserPreference
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class LoginApplicantViewModel(private val hireHubUseCase: NewHirehubUseCase, private val userPreference: UserPreference): ViewModel() {

    private val _stateSuccess = MutableLiveData<SignInResponse>()
    val stateSuccess: LiveData<SignInResponse> = _stateSuccess

    private val _stateLoading = MutableLiveData<Boolean>()
    val stateLoading: LiveData<Boolean> = _stateLoading

    private val _stateError = MutableLiveData<String>()
    val stateError: LiveData<String> = _stateError

    private val _stateToken = MutableLiveData<String>()
    val stateToken: LiveData<String> = _stateToken

    fun loginApplicant(username: String, password: String) {
        hireHubUseCase.loginUser(username, password).onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    _stateLoading.postValue(true)
                }
                is Resource.Success -> {
                    _stateLoading.postValue(false)
                    resource.data?.let {
                        _stateSuccess.postValue(it)
                    }
                }
                is Resource.Error -> {
                    _stateLoading.postValue(false)
                    _stateError.postValue(resource.message.orEmpty())
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getStreamToken(token: String) {
        hireHubUseCase.getStreamToken(token).onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    _stateLoading.postValue(true)
                }
                is Resource.Error -> {
                    _stateLoading.postValue(false)
                    _stateError.postValue(resource.message.orEmpty())
                }
                is Resource.Success -> {
                    _stateLoading.postValue(false)
                    _stateToken.postValue(resource.data?.data?.tokenStream)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun saveUser(user: User) {
        viewModelScope.launch {
            userPreference.saveUser(user)
        }
    }

}