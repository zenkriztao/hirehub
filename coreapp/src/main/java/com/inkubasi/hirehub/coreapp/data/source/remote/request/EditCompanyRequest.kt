package com.inkubasi.hirehub.coreapp.data.source.remote.request

data class EditCompanyRequest(
    val name: String,
    val location: String,
    val office: String,
    val webUrl: String,
    val industry: String,
    val employee: String,
    val description: String
)
