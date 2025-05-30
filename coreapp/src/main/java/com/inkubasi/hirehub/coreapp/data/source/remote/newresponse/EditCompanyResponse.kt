package com.inkubasi.hirehub.coreapp.data.source.remote.newresponse

data class EditCompanyResponse(
    val success: Boolean? = null,
    val statusCode: Int? = null,
    val message: String? = null,
    val data: CompanyData? = null
){
    data class CompanyData(
        val company: Company? = null
    )

    data class Company(
        val id: Int? = null,
        val username: String,
        val email: String? = null,
        val phone: String? = null,
        val location: String? = null,
        val language: String? = null,
        val name: String? = null,
        val employee: String? = null,
        val imageUrl: String? = null,
        val office: String? = null,
        val webUrl: String? = null
    )
}


