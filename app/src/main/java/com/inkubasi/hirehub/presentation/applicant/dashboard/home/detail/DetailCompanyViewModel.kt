package com.inkubasi.hirehub.presentation.applicant.dashboard.home.detail

import androidx.lifecycle.*
import com.inkubasi.hirehub.coreapp.data.source.Resource
import com.inkubasi.hirehub.coreapp.data.source.remote.newresponse.GetProfileCompanyResponse
import com.inkubasi.hirehub.coreapp.domain.model.User
import com.inkubasi.hirehub.coreapp.domain.usecase.NewHirehubUseCase
import com.inkubasi.hirehub.coreapp.ui.UserPreference
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class DetailCompanyViewModel(
    private val hireHubUseCase: NewHirehubUseCase,
    private val userPreference: UserPreference
) : ViewModel() {

    fun getUser(): LiveData<User> = userPreference.getUser().asLiveData()

    private val _stateApplicantAccept = MutableLiveData<String>()
    val stateApplicantAccept: LiveData<String> = _stateApplicantAccept

    private val _stateApplicantReject = MutableLiveData<Unit>()
    val stateApplicantReject: LiveData<Unit> = _stateApplicantReject

    private val _stateError = MutableLiveData<Unit>()
    val stateError: LiveData<Unit> = _stateError

    private val _stateLoading = MutableLiveData<Boolean>()
    val stateLoading: LiveData<Boolean> = _stateLoading

    private val _stateProfile = MutableLiveData<GetProfileCompanyResponse>()
    val stateProfile: LiveData<GetProfileCompanyResponse> = _stateProfile


    fun applicantRejectOffer(token: String, companyId: String) {
        hireHubUseCase.applicantRejectOffer(token, companyId).onEach { resource ->
            when (resource) {
                is Resource.Success -> {
                    _stateLoading.postValue(false)
                    _stateApplicantReject.postValue(Unit)
                }
                is Resource.Loading -> {
                    _stateLoading.postValue(true)
                }
                is Resource.Error -> {
                    _stateLoading.postValue(false)
                    _stateError.postValue(Unit)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun applicantAcceptOffer(token: String, companyId: String) {
        hireHubUseCase.applicantAcceptOffer(token, companyId).onEach { resource ->
            when (resource) {
                is Resource.Success -> {
                    _stateLoading.postValue(false)
                    _stateApplicantAccept.postValue(resource.data?.data?.offer?.company?.userId.toString())
                }
                is Resource.Loading -> {
                    _stateLoading.postValue(true)
                }
                is Resource.Error -> {
                    _stateLoading.postValue(false)
                    _stateError.postValue(Unit)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getDetailCompanyById(token: String, id: String) {
        hireHubUseCase.getProfileCompanyById(token, id).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _stateLoading.value = true
                }
                is Resource.Error -> {
                    _stateLoading.value = false
                }
                is Resource.Success -> {
                    _stateLoading.value = false
                    result.data?.let {
                        _stateProfile.postValue(it)
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

}