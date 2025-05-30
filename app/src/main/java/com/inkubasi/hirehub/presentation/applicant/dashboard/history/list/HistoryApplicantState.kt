package com.inkubasi.hirehub.presentation.applicant.dashboard.history.list

import com.inkubasi.hirehub.coreapp.data.source.remote.newresponse.GetOfferHistoryApplicant

data class HistoryApplicantState(
    val isLoading: Boolean = false,
    val history: List<GetOfferHistoryApplicant.Offer>? = null,
    val error: String = ""
)