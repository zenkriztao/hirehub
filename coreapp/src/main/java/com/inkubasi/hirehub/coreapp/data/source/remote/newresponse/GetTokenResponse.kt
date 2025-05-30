package com.inkubasi.hirehub.coreapp.data.source.remote.newresponse

data class GetTokenResponse(
    val success: Boolean,
    val statusCode: Int,
    val message: String,
    val data: ApiData
) {
    data class ApiData(
        val tokenStream: String
    )
}

