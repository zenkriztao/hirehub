package com.inkubasi.hirehub.presentation.applicant.dashboard.profile

data class UploadPhotoApplicantState(
    val isLoading : Boolean = false,
    val profileImage: String? = null,
    val error: String? = null
)
