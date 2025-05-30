package com.inkubasi.hirehub.coreapp.data.source.remote.newresponse

data class UploadImageCompanyResponse(
    val success: Boolean? = null,
    val statusCode: Int? = null,
    val message: String? = null,
    val data: Data? = null
){
    data class Data(
        val company: Company? = null
    )

    data class Company(
        val imageUrl: String? = null
    )
}


