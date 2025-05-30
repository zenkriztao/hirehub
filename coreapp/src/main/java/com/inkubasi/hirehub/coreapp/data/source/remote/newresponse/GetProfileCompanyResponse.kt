package com.inkubasi.hirehub.coreapp.data.source.remote.newresponse

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetProfileCompanyResponse(
    val success: Boolean? = null,
    val statusCode: Int? = null,
    val message: String? = null,
    val data: CompanyData? = null
) : Parcelable {

    @Parcelize
    data class CompanyData(
        val company: CompanyInfo? = null
    ): Parcelable


    @Parcelize
    data class CompanyInfo(
        val id: Int? = null,
        val username: String? = null,
        val email: String? = null,
        val phone: String?= null,
        val location: String? = null,
        val language: String? = null,
        val name: String? = null,
        val employee: String? = null,
        val description : String? = null,
        val imageUrl: String? = null,
        val office: String? = null,
        val webUrl: String? = null
    ) : Parcelable
}


