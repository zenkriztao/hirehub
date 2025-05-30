package com.inkubasi.hirehub.presentation.company.register

import com.inkubasi.hirehub.coreapp.data.source.remote.newresponse.SignUpResponse


data class RegisterCompanyState (
    val isLoading : Boolean = false,
    val success: SignUpResponse? = null,
    val error: String = ""
)