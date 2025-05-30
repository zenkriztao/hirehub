package com.inkubasi.hirehub.coreapp.data.source.remote.newresponse


data class GetOfferHistoryCompany(
    val success: Boolean,
    val statusCode: Int,
    val message: String,
    val data: Data
){
    data class Data(
        val offer: List<Offer>
    )

    data class Offer(
        val id: Int,
        val applicantId: Int,
        val name: String,
        val field: String?= null,
        val phase: Int? = null,
        val status: String? = null,
        val updatedAt: String? = null
    ) {
        val imageUrl: String?= null
    }

}

