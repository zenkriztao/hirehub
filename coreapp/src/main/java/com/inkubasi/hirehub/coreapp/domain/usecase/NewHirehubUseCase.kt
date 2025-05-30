package com.inkubasi.hirehub.coreapp.domain.usecase

import com.inkubasi.hirehub.coreapp.data.source.Resource
import com.inkubasi.hirehub.coreapp.data.source.remote.newresponse.*
import com.inkubasi.hirehub.coreapp.data.source.remote.request.ApplicantRecommenderRequest
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface NewHirehubUseCase {


    fun registerApplicant(username: String, password: String): Flow<Resource<SignUpResponse>>

    fun registerCompany(username: String, password: String): Flow<Resource<SignUpResponse>>

    fun loginUser(username: String, password: String): Flow<Resource<SignInResponse>>

    fun getProfileApplicantSelf(token: String): Flow<Resource<GetProfileApplicantResponse>>

    fun getProfileApplicantById(
        token: String,
        id: String
    ): Flow<Resource<GetProfileApplicantResponse>>

    fun getProfileCompanyById(
        token: String,
        id: String
    ): Flow<Resource<GetProfileCompanyResponse>>

    fun getProfileCompanySelf(token: String): Flow<Resource<GetProfileCompanyResponse>>

    fun uploadImage(
        token: String,
        imageFile: MultipartBody.Part
    ): Flow<Resource<UploadImageResponse>>

    fun uploadImageCompany(
        token: String,
        imageFile: MultipartBody.Part
    ): Flow<Resource<UploadImageCompanyResponse>>

    fun uploadCv(token: String, pdfFile: MultipartBody.Part): Flow<Resource<UploadCVResponse>>

    fun editProfileCompany(
        token: String,
        name: String,
        location: String,
        office: String,
        webUrl: String,
        industry: String,
        employee: String,
        description: String
    ): Flow<Resource<EditCompanyResponse>>

    fun editProfileApplicant(token: String, name: String, email: String, dateOfBirth: String, language: List<String>, field: String,
                             skills: List<String>, salaryMin: Int, location: String,
                             institution: String , degree: String, phone: String, summary: String ) : Flow<Resource<EditApplicantResponse>>

    fun getOfferOnProsesHistoryCompany(token: String) : Flow<Resource<GetOfferHistoryCompany>>

    fun getOfferAcceptHistoryCompany(token: String) : Flow<Resource<GetOfferHistoryCompany>>

    fun getOfferRejectHistoryCompany(token: String): Flow<Resource<GetOfferHistoryCompany>>

    fun getOfferPendingHistoryApplicant(token: String): Flow<Resource<GetOfferHistoryApplicant>>

    fun getOfferAcceptHistoryApplicant(token: String): Flow<Resource<GetOfferHistoryApplicant>>

    fun getOfferOnProsesHistoryApplicant(token: String): Flow<Resource<GetOfferHistoryApplicant>>

    fun getOfferRejectHistoryApplicant(token: String): Flow<Resource<GetOfferHistoryApplicant>>

    fun applicantAcceptOffer(token: String, id: String): Flow<Resource<OfferResponse>>

    fun applicantRejectOffer(token: String, id: String): Flow<Resource<OfferResponse>>

    fun companyAcceptOffer(token: String, id: String): Flow<Resource<OfferResponse>>

    fun companyRejectOffer(token: String, id: String): Flow<Resource<OfferResponse>>

    fun getApplicantList(
        token: String,
        currentPage: Int,
        search: String
    ): Flow<Resource<ApplicantListResponse>>

    fun createOfferApplicant(
        token: String,
        applicantId: String
    ): Flow<Resource<CreateOfferResponse>>

    fun getStreamToken(token: String): Flow<Resource<GetTokenResponse>>

    fun getApplicantRecommendation(
        token: String,
        applicantRecommenderRequest: ApplicantRecommenderRequest
    ): Flow<Resource<ApplicantRecommenderResponse>>

}