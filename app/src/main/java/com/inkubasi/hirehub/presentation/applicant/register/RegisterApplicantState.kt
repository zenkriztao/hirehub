package com.inkubasi.hirehub.presentation.applicant.register

import com.inkubasi.hirehub.coreapp.data.source.remote.newresponse.SignUpResponse


data class RegisterApplicantState (
    val isLoading : Boolean = false,
    val success: SignUpResponse? = null,
    val error: String = ""
)
