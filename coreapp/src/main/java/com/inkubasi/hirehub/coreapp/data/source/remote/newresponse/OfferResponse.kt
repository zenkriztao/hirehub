package com.inkubasi.hirehub.coreapp.data.source.remote.newresponse

data class OfferResponse(
    val success: Boolean,
    val statusCode: Int,
    val message: String,
    val data: Data
) {
    data class Data(
        val offer: Offer
    )

    data class Offer(
        val id: Int,
        val phase: Int,
        val status: String,
        val updatedBy: String,
        val company: Company,
        val applicant: Applicant
    )

    data class Company(
        val userId: Int,
        val companyId: Int,
        val companyName: String?
    )

    data class Applicant(
        val userId: Int,
        val applicantId: Int,
        val applicantName: String?
    )
}


