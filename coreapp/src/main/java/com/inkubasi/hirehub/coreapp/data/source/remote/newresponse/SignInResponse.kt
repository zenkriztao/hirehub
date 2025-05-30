package com.inkubasi.hirehub.coreapp.data.source.remote.newresponse

data class SignInResponse(
    val success: Boolean? = null,
    val statusCode: Int? = null,
    val message: String? = null,
    val data: ApiData? = null
){
    data class ApiData(
        val user: UserData? = null,
        val token: String? = null
    )

    data class UserData(
       val id: Int? = null,
       val username: String? = null,
       val roleId: Int? = null,
       val roleName: String? = null,
       val companyId: Int? = null,
       val applicantId: Int? = null,
       val isValid: Int? = null
    )
}


