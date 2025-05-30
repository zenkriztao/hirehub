package com.inkubasi.hirehub.presentation.company.dashboard.chat

import androidx.lifecycle.*
import com.inkubasi.hirehub.coreapp.data.source.Resource
import com.inkubasi.hirehub.coreapp.data.source.remote.newresponse.GetProfileCompanyResponse
import com.inkubasi.hirehub.coreapp.domain.model.User
import com.inkubasi.hirehub.coreapp.domain.usecase.NewHirehubUseCase
import com.inkubasi.hirehub.coreapp.ui.UserPreference
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ChatCompanyViewModel(
    private val userPreference: UserPreference,
    private val hirehubUseCase: NewHirehubUseCase
) : ViewModel() {

    fun getUser(): LiveData<User> = userPreference.getUser().asLiveData()

    private val _stateLoading = MutableLiveData<Boolean>()
    val stateLoading: LiveData<Boolean> = _stateLoading

    private val _stateProfile = MutableLiveData<GetProfileCompanyResponse.CompanyInfo>()
    val stateProfile: LiveData<GetProfileCompanyResponse.CompanyInfo> = _stateProfile

    fun getCompanyProfileSelf(token: String) {
        hirehubUseCase.getProfileCompanySelf(token).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _stateLoading.postValue(true)
                }
                is Resource.Error -> {
                    _stateLoading.postValue(false)
                }
                is Resource.Success -> {
                    _stateLoading.postValue(false)
                    result.data?.data?.company.let {
                        _stateProfile.postValue(it)
                    }
                }
            }
        }.launchIn(viewModelScope)
    }
}