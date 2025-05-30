package com.inkubasi.hirehub.coreapp.data.source.remote.newresponse

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class ApplicantListResponse(
    val success: Boolean,
    val statusCode: Int,
    val message: String,
    val data: ApiData
) : Parcelable {

    @Parcelize
    data class ApiData(
        val applicant: List<Applicant>,
        val pagination: Pagination
    ) : Parcelable

    @Parcelize
    data class Applicant(
        val id: Int,
        val username: String,
        val name: String? = null,
        val summary: String? = null,
        val field: String? = null,
        val salaryMin: Int? = null,
        val imageUrl: String? = null
    ) : Parcelable

    @Parcelize
    data class Pagination(
        val page: Int,
        val pageSize: Int,
        val offset: Int,
        val total: Int
    ) : Parcelable

}

