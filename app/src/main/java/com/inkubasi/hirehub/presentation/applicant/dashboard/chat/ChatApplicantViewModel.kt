package com.inkubasi.hirehub.presentation.applicant.dashboard.chat


import androidx.lifecycle.*
import com.inkubasi.hirehub.coreapp.data.source.Resource
import com.inkubasi.hirehub.coreapp.data.source.remote.newresponse.GetProfileApplicantResponse
import com.inkubasi.hirehub.coreapp.domain.model.User
import com.inkubasi.hirehub.coreapp.domain.usecase.NewHirehubUseCase
import com.inkubasi.hirehub.coreapp.ui.UserPreference
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ChatApplicantViewModel(
    private val userPreference: UserPreference,
    private val hirehubUseCase: NewHirehubUseCase
) : ViewModel() {

    fun getUser(): LiveData<User> = userPreference.getUser().asLiveData()

    private val _stateLoading = MutableLiveData<Boolean>()
    val stateLoading: LiveData<Boolean> = _stateLoading

    private val _stateProfile = MutableLiveData<GetProfileApplicantResponse.ApplicantData>()
    val stateProfile: LiveData<GetProfileApplicantResponse.ApplicantData> = _stateProfile

    fun getProfileApplicantSelf(token: String) {
        hirehubUseCase.getProfileApplicantSelf(token).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _stateLoading.postValue(true)
                }
                is Resource.Error -> {
                    _stateLoading.postValue(false)
                }
                is Resource.Success -> {
                    _stateLoading.postValue(false)
                    result.data?.data?.applicant.let {
                        _stateProfile.postValue(it)
                    }
                }
            }
        }.launchIn(viewModelScope)
    }
}