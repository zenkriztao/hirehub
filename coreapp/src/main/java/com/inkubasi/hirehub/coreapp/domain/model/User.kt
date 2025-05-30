package com.inkubasi.hirehub.coreapp.domain.model


data class User(
    val name: String,
    val token: String,
    val id: String,
    val isApplicant: Boolean,
    val isLogin: Boolean,
    val tokenStream: String
)