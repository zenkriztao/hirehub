package com.inkubasi.hirehub.presentation.company.dashboard.profile

data class UploadPhotoCompanyState(
    val isLoading : Boolean = false,
    val profileImage: String? = null,
    val error: String? = null
)
