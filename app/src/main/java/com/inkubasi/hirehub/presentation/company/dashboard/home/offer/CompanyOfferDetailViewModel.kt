package com.inkubasi.hirehub.presentation.company.dashboard.home.offer

import androidx.lifecycle.*
import com.inkubasi.hirehub.coreapp.data.source.Resource
import com.inkubasi.hirehub.coreapp.data.source.remote.newresponse.GetProfileApplicantResponse
import com.inkubasi.hirehub.coreapp.domain.model.User
import com.inkubasi.hirehub.coreapp.domain.usecase.NewHirehubUseCase
import com.inkubasi.hirehub.coreapp.ui.UserPreference
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class CompanyOfferDetailViewModel(
    private val hireHubUseCase: NewHirehubUseCase,
    private val userPreference: UserPreference
) : ViewModel() {


    private val _stateLoading = MutableLiveData<Boolean>()
    val stateLoading: LiveData<Boolean> = _stateLoading

    private val _stateSuccess = MutableLiveData<GetProfileApplicantResponse>()
    val stateSuccess: LiveData<GetProfileApplicantResponse> = _stateSuccess

    private val _stateProcessSuccess = MutableLiveData<Unit>()
    val stateProcessSuccess: LiveData<Unit> = _stateProcessSuccess

    private val _stateProcessError = MutableLiveData<Unit>()
    val stateProcessError: LiveData<Unit> = _stateProcessError

    fun getUser(): LiveData<User> = userPreference.getUser().asLiveData()


    fun getDetailApplicantById(token: String, id: String) {
        hireHubUseCase.getProfileApplicantById(token, id).onEach { result ->
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
                        _stateSuccess.postValue(it)
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    fun createOfferApplicant(token: String, applicantId: String) {
        hireHubUseCase.createOfferApplicant(token, applicantId).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _stateLoading.value = true
                }
                is Resource.Error -> {
                    _stateProcessError.postValue(Unit)
                    _stateLoading.value = false
                }
                is Resource.Success -> {
                    _stateLoading.value = false
                    result.data?.let {
                        _stateProcessSuccess.postValue(Unit)
                    }
                }
            }
        }.launchIn(viewModelScope)
    }


}