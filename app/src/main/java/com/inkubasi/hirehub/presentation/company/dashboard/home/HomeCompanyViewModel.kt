package com.inkubasi.hirehub.presentation.company.dashboard.home

import androidx.lifecycle.*
import com.inkubasi.hirehub.coreapp.data.source.Resource
import com.inkubasi.hirehub.coreapp.data.source.remote.newresponse.ApplicantListResponse
import com.inkubasi.hirehub.coreapp.data.source.remote.newresponse.ApplicantRecommenderResponse
import com.inkubasi.hirehub.coreapp.data.source.remote.request.ApplicantRecommenderRequest
import com.inkubasi.hirehub.coreapp.domain.model.User
import com.inkubasi.hirehub.coreapp.domain.usecase.NewHirehubUseCase
import com.inkubasi.hirehub.coreapp.ui.UserPreference
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class HomeCompanyViewModel(
    private val hireHubUseCase: NewHirehubUseCase,
    private val userPreference: UserPreference
) : ViewModel() {


    private val baseList: ArrayList<ApplicantListResponse.Applicant> = arrayListOf()
    private var currentPage: Int = 1
    private var lastPage: Boolean = false

    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> = _loadingState

    private val _content = MutableLiveData<List<ApplicantListResponse.Applicant>>()
    val contentData: LiveData<List<ApplicantListResponse.Applicant>> = _content

    private val _contentFilter = MutableLiveData<List<ApplicantRecommenderResponse.Applicant>>()
    val contentFilter: LiveData<List<ApplicantRecommenderResponse.Applicant>> = _contentFilter

    fun fetchApplicant(token: String, search: String) {
        if (search != "") {
            refreshData()
        } else {
            if (lastPage) return
        }
        hireHubUseCase.getApplicantList(token, currentPage, search).onEach { result ->
            when (result) {
                is Resource.Error -> {
                    _loadingState.postValue(false)
                }
                is Resource.Loading -> {
                    _loadingState.postValue(true)
                }
                is Resource.Success -> {
                    _loadingState.postValue(false)
                    baseList.addAll(result.data?.data?.applicant.orEmpty())
                    currentPage += 1
                    lastPage = (result.data?.data?.applicant?.size ?: 0) < 10
                    reloadContent()
                }
            }
        }.launchIn(viewModelScope)
    }

    fun fetchFilter(token: String, applicantRecommenderRequest: ApplicantRecommenderRequest) {
        hireHubUseCase.getApplicantRecommendation(token, applicantRecommenderRequest)
            .onEach { result ->
                when (result) {
                    is Resource.Error -> {
                        _loadingState.postValue(false)
                    }
                    is Resource.Loading -> {
                        _loadingState.postValue(true)
                    }
                    is Resource.Success -> {
                        _loadingState.postValue(false)
                        result.data?.let {
                            _contentFilter.postValue(it.data ?: emptyList())
                        }
                        refreshData()
                    }
                }
            }.launchIn(viewModelScope)
    }

    fun refreshData() {
        baseList.clear()
        lastPage = false
        currentPage = 1
    }

    private fun reloadContent() {
        _content.postValue(baseList.toMutableList())
    }

    fun getUser(): LiveData<User> {
        return userPreference.getUser().asLiveData()
    }


}