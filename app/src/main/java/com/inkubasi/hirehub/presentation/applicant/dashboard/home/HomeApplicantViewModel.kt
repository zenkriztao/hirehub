package com.inkubasi.hirehub.presentation.applicant.dashboard.home


import androidx.lifecycle.*
import com.inkubasi.hirehub.coreapp.data.source.Resource
import com.inkubasi.hirehub.coreapp.data.source.remote.newresponse.GetOfferHistoryApplicant
import com.inkubasi.hirehub.coreapp.domain.model.User
import com.inkubasi.hirehub.coreapp.domain.usecase.NewHirehubUseCase
import com.inkubasi.hirehub.coreapp.ui.UserPreference
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class HomeApplicantViewModel(
    private val hireHubUseCase: NewHirehubUseCase,
    private val userPreference: UserPreference
) : ViewModel() {


    private val _stateCompanyList = MutableLiveData<List<GetOfferHistoryApplicant.Offer>>()
    val stateCompanyList: LiveData<List<GetOfferHistoryApplicant.Offer>> = _stateCompanyList


    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> = _loadingState


    fun getUser(): LiveData<User> {
        return userPreference.getUser().asLiveData()
    }

    fun getOfferHistoryByAction(token: String) {
        hireHubUseCase.getOfferPendingHistoryApplicant(token).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _loadingState.postValue(false)
                    _stateCompanyList.postValue(result.data?.data?.offer ?: emptyList())
                }
                is Resource.Error -> {
                    _loadingState.postValue(false)
                }
                is Resource.Loading -> {
                    _loadingState.postValue(true)
                }
            }
        }.launchIn(viewModelScope)
    }

}