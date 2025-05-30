package com.inkubasi.hirehub.presentation.company.dashboard.history.list

import com.inkubasi.hirehub.coreapp.data.source.remote.newresponse.GetOfferHistoryCompany

data class HistoryCompanyState(
    val isLoading: Boolean = false,
    val history: List<GetOfferHistoryCompany.Offer>? = null,
    val error: String = ""
)