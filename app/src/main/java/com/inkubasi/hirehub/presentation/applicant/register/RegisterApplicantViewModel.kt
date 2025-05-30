package com.inkubasi.hirehub.presentation.applicant.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inkubasi.hirehub.coreapp.data.source.Resource
import com.inkubasi.hirehub.coreapp.domain.usecase.NewHirehubUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class RegisterApplicantViewModel(private val hireHubUseCase: NewHirehubUseCase): ViewModel() {
    private val _state = MutableLiveData<RegisterApplicantState>()
    val state : LiveData<RegisterApplicantState> = _state

    fun registerApplicant(username:String, password:String){
        hireHubUseCase.registerApplicant(username, password).onEach {
                resource ->
            when(resource){
                is Resource.Loading -> {
                    _state.value = RegisterApplicantState(isLoading = true)
                }
                is Resource.Success -> {
                    _state.value = RegisterApplicantState(success = resource.data)
                }
                is Resource.Error -> {
                    _state.value = RegisterApplicantState(error = resource.message.orEmpty())
                }
            }
        }.launchIn(viewModelScope)
    }
}