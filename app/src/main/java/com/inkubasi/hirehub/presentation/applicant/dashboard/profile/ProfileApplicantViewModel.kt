package com.inkubasi.hirehub.presentation.applicant.dashboard.profile

import androidx.lifecycle.*
import com.inkubasi.hirehub.coreapp.data.source.Resource
import com.inkubasi.hirehub.coreapp.domain.model.User
import com.inkubasi.hirehub.coreapp.domain.usecase.NewHirehubUseCase
import com.inkubasi.hirehub.coreapp.ui.UserPreference
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import okhttp3.MultipartBody

class ProfileApplicantViewModel(
    private val hireHubUseCase: NewHirehubUseCase,
    private val userPreference: UserPreference
    ): ViewModel() {

    private val _state = MutableLiveData<ProfileApplicantState>()
    val state : LiveData<ProfileApplicantState> = _state

    private val _stateProfile = MutableLiveData<UploadPhotoApplicantState>()
    val stateProfile : LiveData<UploadPhotoApplicantState> = _stateProfile

     fun getUser(): LiveData<User> {
        return userPreference.getUser().asLiveData()
    }

    fun getProfileApplicantByUsername(token : String){
        hireHubUseCase.getProfileApplicantSelf(token).onEach {
                result ->
            when(result){
                is Resource.Loading -> {
                    _state.value = ProfileApplicantState(isLoading = true)
                }
                is Resource.Error -> {
                    _state.value = result.message?.let { ProfileApplicantState(error = it) }
                }
                is Resource.Success -> {
                    _state.value = ProfileApplicantState(profile = result.data)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun uploadPhotoProfile(token: String, file : MultipartBody.Part){
        hireHubUseCase.uploadImage(token, file).onEach {result ->
            when(result){
                is Resource.Loading -> {
                    _stateProfile.value = UploadPhotoApplicantState(isLoading = true)
                }
                is Resource.Error -> {
                    _stateProfile.value = UploadPhotoApplicantState(error = result.message.orEmpty())
                }
                is Resource.Success -> {
                    _stateProfile.value = UploadPhotoApplicantState(profileImage = result.data?.data?.applicant?.imageUrl)
                }
            }
        }.launchIn(viewModelScope)
    }

}