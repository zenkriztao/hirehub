package com.inkubasi.hirehub.presentation.company.dashboard.history.detail

import androidx.lifecycle.*
import com.inkubasi.hirehub.coreapp.data.source.Resource
import com.inkubasi.hirehub.coreapp.data.source.remote.newresponse.GetProfileApplicantResponse
import com.inkubasi.hirehub.coreapp.domain.model.User
import com.inkubasi.hirehub.coreapp.domain.usecase.NewHirehubUseCase
import com.inkubasi.hirehub.coreapp.ui.UserPreference
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class HistoryDetailViewModel(
    private val hireHubUseCase: NewHirehubUseCase,
    private val userPreference: UserPreference
) : ViewModel() {

    private val _stateLoading = MutableLiveData<Boolean>()
    val stateLoading: LiveData<Boolean> = _stateLoading

    private val _stateSuccess = MutableLiveData<GetProfileApplicantResponse>()
    val stateSuccess: LiveData<GetProfileApplicantResponse> = _stateSuccess

    private val _stateAcceptSuccess = MutableLiveData<Unit>()
    val stateAcceptSuccess: LiveData<Unit> = _stateAcceptSuccess

    private val _stateRejectSuccess = MutableLiveData<Unit>()
    val stateRejectSuccess: LiveData<Unit> = _stateRejectSuccess


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

    fun acceptOfferCompany(token: String, id: String) {
        hireHubUseCase.companyAcceptOffer(token, id).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _stateLoading.value = true
                }
                is Resource.Error -> {
                    _stateLoading.value = false
                }
                is Resource.Success -> {
                    _stateLoading.value = false
                    _stateAcceptSuccess.postValue(Unit)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun rejectOfferCompany(token: String, id: String) {
        hireHubUseCase.companyRejectOffer(token, id).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _stateLoading.value = true
                }
                is Resource.Error -> {
                    _stateLoading.value = false
                }
                is Resource.Success -> {
                    _stateLoading.value = false
                    _stateRejectSuccess.postValue(Unit)
                }
            }
        }.launchIn(viewModelScope)
    }

}