package com.inkubasi.hirehub.coreapp.data.source.remote.newresponse

data class GetOfferHistoryApplicant(
    val success: Boolean,
    val statusCode: Int,
    val message: String,
    val data: OfferData
){

    data class OfferData(
        val offer: List<Offer>
    )

    data class Offer(
        val id: Int,
        val companyId: Int,
        val name: String? = null,
        val imageUrl: String? = null,
        val phase: Int? = null,
        val status: String?= null,
        val updatedAt: String? = null
    )

}
