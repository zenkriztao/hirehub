package com.inkubasi.hirehub.presentation.company.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inkubasi.hirehub.coreapp.data.source.Resource
import com.inkubasi.hirehub.coreapp.domain.usecase.NewHirehubUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class RegisterCompanyViewModel(private val hireHubUseCase: NewHirehubUseCase): ViewModel() {
    private val _state = MutableLiveData<RegisterCompanyState>()
    val state : LiveData<RegisterCompanyState> = _state
    fun registerCompany(username:String, password:String){
        hireHubUseCase.registerCompany(username, password).onEach {
                resource ->
            when(resource){
                is Resource.Loading -> {
                    _state.value = RegisterCompanyState(isLoading = true)
                }
                is Resource.Success -> {
                    _state.value = RegisterCompanyState(success = resource.data)
                }
                is Resource.Error -> {
                    _state.value = RegisterCompanyState(error = resource.message.orEmpty())
                }
            }
        }.launchIn(viewModelScope)
    }
}