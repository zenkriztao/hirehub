package com.inkubasi.hirehub.presentation.company.dashboard.history.list

import androidx.lifecycle.*
import com.inkubasi.hirehub.coreapp.data.source.Resource
import com.inkubasi.hirehub.coreapp.domain.model.User
import com.inkubasi.hirehub.coreapp.domain.usecase.NewHirehubUseCase
import com.inkubasi.hirehub.coreapp.ui.UserPreference
import com.inkubasi.hirehub.coreapp.utils.constant.HistoryType
import kotlinx.coroutines.launch

class HistoryCompanyViewModel(
    private val hireHubUseCase: NewHirehubUseCase,
    private val userPreference: UserPreference
) : ViewModel() {

    private val _state = MutableLiveData<HistoryCompanyState>()
    val state: LiveData<HistoryCompanyState> = _state

    fun getUser(): LiveData<User> = userPreference.getUser().asLiveData()

    fun getOfferHistoryByAction(action: String, token: String) {
        val offerHistoryFlow = when (action) {
            HistoryType.SUCCESS -> hireHubUseCase.getOfferAcceptHistoryCompany(token)
            HistoryType.ON_PROCESS -> hireHubUseCase.getOfferOnProsesHistoryCompany(token)
            HistoryType.REJECT -> hireHubUseCase.getOfferRejectHistoryCompany(token)
            else -> throw IllegalArgumentException("Tindakan tidak valid")
        }

        viewModelScope.launch {
            offerHistoryFlow.collect { result ->
                val historyState = when (result) {
                    is Resource.Success -> HistoryCompanyState(history = result.data?.data?.offer)
                    is Resource.Error -> HistoryCompanyState(error = result.message ?: "")
                    is Resource.Loading -> HistoryCompanyState(isLoading = true)
                }
                _state.value = historyState
            }
        }
    }
}
