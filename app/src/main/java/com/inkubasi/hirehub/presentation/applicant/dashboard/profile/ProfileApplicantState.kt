package com.inkubasi.hirehub.presentation.applicant.dashboard.profile

import com.inkubasi.hirehub.coreapp.data.source.remote.newresponse.GetProfileApplicantResponse

data class ProfileApplicantState(
    val isLoading : Boolean = false,
    val profile: GetProfileApplicantResponse? = null,
    val error: String = ""
)