package com.inkubasi.hirehub.coreapp.data.source.remote.newresponse

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetProfileApplicantResponse(
    val success: Boolean? = null,
    val statusCode: Int? = null,
    val message: String? = null,
    val data: ApiData? = null
) : Parcelable {

    @Parcelize
    data class ApiData(
        val applicant: ApplicantData? = null
    ): Parcelable

    @Parcelize
    data class ApplicantData(
        val id: Int? = null,
        val username: String? = null,
        val email: String? = null,
        val phone: String? = null,
        val location: String? = null,
        val language: List<String>? = null,
        val name: String? = null,
        val summary: String? = null,
        val field: String? = null,
        val dateOfBirth: String? = null,
        val age: Int? = null,
        val institution: String? = null,
        val degree: String? = null,
        val skills: List<String>? = null,
        val salaryMin: Int? = null,
        val openToWork: Boolean? = null,
        val imageUrl: String? = null,
        val pdfUrl: String? = null,
    ): Parcelable

}

