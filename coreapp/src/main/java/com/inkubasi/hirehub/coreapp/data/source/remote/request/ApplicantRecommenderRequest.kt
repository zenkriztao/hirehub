package com.inkubasi.hirehub.coreapp.data.source.remote.request

data class ApplicantRecommenderRequest(
    val ageFilter: List<Int>,
    val ageTolerance: Int,
    val skillFilter: List<String>,
    val languageFilter: List<String>,
    val salaryFilter: List<Int>,
    val salaryTolerance: Int
)