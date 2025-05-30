package com.inkubasi.hirehub.coreapp.data.source.remote.newresponse

data class UploadImageResponse(
    val success: Boolean? = null,
    val statusCode: Int? = null,
    val message: String? = null,
    val data: Data? = null
){
    data class Data(
        val applicant: Applicant? = null
    )

    data class Applicant(
        val imageUrl: String? = null
    )
}


