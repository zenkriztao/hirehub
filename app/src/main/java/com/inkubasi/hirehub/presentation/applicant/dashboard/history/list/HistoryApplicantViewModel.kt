package com.inkubasi.hirehub.presentation.applicant.dashboard.history.list

import androidx.lifecycle.*
import com.inkubasi.hirehub.coreapp.data.source.Resource
import com.inkubasi.hirehub.coreapp.domain.model.User
import com.inkubasi.hirehub.coreapp.domain.usecase.NewHirehubUseCase
import com.inkubasi.hirehub.coreapp.ui.UserPreference
import com.inkubasi.hirehub.coreapp.utils.constant.HistoryType
import kotlinx.coroutines.launch


class HistoryApplicantViewModel(
    private val hireHubUseCase: NewHirehubUseCase,
    private val userPreference: UserPreference
): ViewModel() {

   private val _state = MutableLiveData<HistoryApplicantState>()
    val state : LiveData<HistoryApplicantState> = _state

    fun getUser(): LiveData<User> {
        return userPreference.getUser().asLiveData()
    }

    fun getOfferHistoryByAction(action: String, token: String){
        val offerHistoryFlow = when (action) {
            HistoryType.SUCCESS -> hireHubUseCase.getOfferAcceptHistoryApplicant(token)
            HistoryType.ON_PROCESS -> hireHubUseCase.getOfferOnProsesHistoryApplicant(token)
            HistoryType.REJECT -> hireHubUseCase.getOfferRejectHistoryApplicant(token)
            else -> {
                throw IllegalArgumentException("Tindakan tidak valid")
            }
        }

        viewModelScope.launch {
            offerHistoryFlow.collect { result ->
              when(result){
                  is Resource.Success -> {
                      _state.value = HistoryApplicantState(history = result.data?.data?.offer)
                  }
                  is Resource.Error -> {
                      _state.value =
                          HistoryApplicantState(error = result.message ?: "Unexpected error")
                  }
                  is Resource.Loading -> {
                      _state.value = HistoryApplicantState(isLoading = true)
                  }
              }

            }
        }
    }



}