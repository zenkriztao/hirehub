package com.inkubasi.hirehub.presentation.company.dashboard.profile

import androidx.lifecycle.*
import com.inkubasi.hirehub.coreapp.data.source.Resource
import com.inkubasi.hirehub.coreapp.domain.model.User
import com.inkubasi.hirehub.coreapp.domain.usecase.NewHirehubUseCase
import com.inkubasi.hirehub.coreapp.ui.UserPreference
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import okhttp3.MultipartBody

class ProfileCompanyViewModel(
    private val hireHubUseCase: NewHirehubUseCase,
    private val userPreference: UserPreference
    ): ViewModel() {

    private val _state = MutableLiveData<ProfileCompanyState>()
    val state : LiveData<ProfileCompanyState> = _state

    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState : LiveData<Boolean> = _loadingState

    private val _successPhoto = MutableLiveData<String>()
    val successPhoto : LiveData<String> = _successPhoto

    fun getUser(): LiveData<User> {
        return userPreference.getUser().asLiveData()
    }

    fun getProfileCompanyByUsername(token : String){
        hireHubUseCase.getProfileCompanySelf(token).onEach {
            result ->
            when(result){
                is Resource.Loading -> {
                    _state.value = ProfileCompanyState(isLoading = true)
                }
                is Resource.Error -> {
                    _state.value = ProfileCompanyState(error = result.message.orEmpty())
                }
                is Resource.Success -> {
                    _state.value = ProfileCompanyState(profile = result.data)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun uploadPhotoProfile(token: String, file : MultipartBody.Part){
        hireHubUseCase.uploadImageCompany(token, file).onEach {result ->
            when(result){
                is Resource.Loading -> {
                    _loadingState.value = true
                }
                is Resource.Error -> {
                    _loadingState.value = false
                }
                is Resource.Success -> {
                    _loadingState.value = false
                    _successPhoto.postValue(result.data?.data?.company?.imageUrl.orEmpty())
                }
            }
        }.launchIn(viewModelScope)
    }
}