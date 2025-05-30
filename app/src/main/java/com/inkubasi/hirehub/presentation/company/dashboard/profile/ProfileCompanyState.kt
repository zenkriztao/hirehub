package com.inkubasi.hirehub.presentation.company.dashboard.profile

import com.inkubasi.hirehub.coreapp.data.source.remote.newresponse.GetProfileCompanyResponse

data class ProfileCompanyState(
    val isLoading : Boolean = false,
    val profile: GetProfileCompanyResponse? = null,
    val error: String = ""
)