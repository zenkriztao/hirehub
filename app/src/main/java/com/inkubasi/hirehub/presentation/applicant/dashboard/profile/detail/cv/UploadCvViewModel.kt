package com.inkubasi.hirehub.presentation.applicant.dashboard.profile.detail.cv

import androidx.lifecycle.*
import com.inkubasi.hirehub.coreapp.data.source.Resource
import com.inkubasi.hirehub.coreapp.domain.model.User
import com.inkubasi.hirehub.coreapp.domain.usecase.NewHirehubUseCase
import com.inkubasi.hirehub.coreapp.ui.UserPreference
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import okhttp3.MultipartBody

class UploadCvViewModel(
    private val hireHubUseCase: NewHirehubUseCase,
    private val userPreference: UserPreference
): ViewModel() {

    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState : LiveData<Boolean> = _loadingState

    private val _successUpload = MutableLiveData<Unit>()
    val successUpload : LiveData<Unit> = _successUpload

    fun getUser(): LiveData<User> {
        return userPreference.getUser().asLiveData()
    }

     fun uploadCV(token : String, cv: MultipartBody.Part){
        hireHubUseCase.uploadCv(token, cv).onEach {
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
                    _successUpload.postValue(Unit)
                }
            }
        }.launchIn(viewModelScope)
    }

}