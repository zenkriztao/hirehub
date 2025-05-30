package com.inkubasi.hirehub.coreapp.data.source.remote.newresponse

data class ApplicantRecommenderResponse(
    val success: Boolean,
    val statusCode: Int,
    val message: String,
    val data: List<Applicant>? = null
) {
    data class Applicant(
        val id: Int,
        val username: String,
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
        val tools: List<String>? = null,
        val salaryMin: Int? = null,
        val openToWork: Boolean? = null,
        val imageUrl: String? = null,
        val score: List<Double>? = null,
        val totalScore: Double? = null
    )
}


