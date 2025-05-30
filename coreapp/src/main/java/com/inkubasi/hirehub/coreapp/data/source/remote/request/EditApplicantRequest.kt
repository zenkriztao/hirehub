package com.inkubasi.hirehub.coreapp.data.source.remote.request

data class EditApplicantRequest(
    val name: String,
    val email: String,
    val dateOfBirth: String,
    val language: List<String>,
    val field: String,
    val skills: List<String>,
    val salaryMin: Int,
    val location: String,
    val institution : String,
    val degree: String,
    val phone: String,
    val summary: String
)
