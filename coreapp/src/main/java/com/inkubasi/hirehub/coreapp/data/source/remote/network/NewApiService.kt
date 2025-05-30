package com.inkubasi.hirehub.coreapp.data.source.remote.network

import com.inkubasi.hirehub.coreapp.data.source.remote.endpoint.EndPoint.APPLICANT_ACCEPT_OFFER_BY_ID
import com.inkubasi.hirehub.coreapp.data.source.remote.endpoint.EndPoint.APPLICANT_GET_LIST
import com.inkubasi.hirehub.coreapp.data.source.remote.endpoint.EndPoint.APPLICANT_RECOMMENDER_LIST
import com.inkubasi.hirehub.coreapp.data.source.remote.endpoint.EndPoint.APPLICANT_REJECT_OFFER_BY_ID
import com.inkubasi.hirehub.coreapp.data.source.remote.endpoint.EndPoint.COMPANY_ACCEPT_OFFER_BY_ID
import com.inkubasi.hirehub.coreapp.data.source.remote.endpoint.EndPoint.COMPANY_REJECT_OFFER_BY_ID
import com.inkubasi.hirehub.coreapp.data.source.remote.endpoint.EndPoint.CREATE_OFFER_APPLICANT
import com.inkubasi.hirehub.coreapp.data.source.remote.endpoint.EndPoint.EDIT_PROFILE_SELF_APPLICANT
import com.inkubasi.hirehub.coreapp.data.source.remote.endpoint.EndPoint.EDIT_PROFILE_SELF_COMPANY
import com.inkubasi.hirehub.coreapp.data.source.remote.endpoint.EndPoint.GET_PROFILE_APPLICANT
import com.inkubasi.hirehub.coreapp.data.source.remote.endpoint.EndPoint.GET_PROFILE_COMPANY
import com.inkubasi.hirehub.coreapp.data.source.remote.endpoint.EndPoint.GET_PROFILE_SELF_APPLICANT
import com.inkubasi.hirehub.coreapp.data.source.remote.endpoint.EndPoint.GET_PROFILE_SELF_COMPANY
import com.inkubasi.hirehub.coreapp.data.source.remote.endpoint.EndPoint.GET_TOKEN_STREAM
import com.inkubasi.hirehub.coreapp.data.source.remote.endpoint.EndPoint.OFFER_ACCEPT_HISTORY_APPLICANT
import com.inkubasi.hirehub.coreapp.data.source.remote.endpoint.EndPoint.OFFER_ACCEPT_HISTORY_COMPANY
import com.inkubasi.hirehub.coreapp.data.source.remote.endpoint.EndPoint.OFFER_ON_PROSES_HISTORY_APPLICANT
import com.inkubasi.hirehub.coreapp.data.source.remote.endpoint.EndPoint.OFFER_ON_PROSES_HISTORY_COMPANY
import com.inkubasi.hirehub.coreapp.data.source.remote.endpoint.EndPoint.OFFER_PENDING_HISTORY_APPLICANT
import com.inkubasi.hirehub.coreapp.data.source.remote.endpoint.EndPoint.OFFER_REJECT_HISTORY_APPLICANT
import com.inkubasi.hirehub.coreapp.data.source.remote.endpoint.EndPoint.OFFER_REJECT_HISTORY_COMPANY
import com.inkubasi.hirehub.coreapp.data.source.remote.endpoint.EndPoint.SIGN_IN
import com.inkubasi.hirehub.coreapp.data.source.remote.endpoint.EndPoint.SIGN_UP
import com.inkubasi.hirehub.coreapp.data.source.remote.endpoint.EndPoint.UPLOAD_CV
import com.inkubasi.hirehub.coreapp.data.source.remote.endpoint.EndPoint.UPLOAD_IMAGE
import com.inkubasi.hirehub.coreapp.data.source.remote.endpoint.EndPoint.UPLOAD_IMAGE_COMPANY
import com.inkubasi.hirehub.coreapp.data.source.remote.newresponse.*
import com.inkubasi.hirehub.coreapp.data.source.remote.request.*
import okhttp3.MultipartBody
import retrofit2.http.*

interface NewApiService {

    @POST(SIGN_IN)
    suspend fun signIn(@Body signInRequest: SignInRequest): SignInResponse

    @POST(SIGN_UP)
    suspend fun signUp(@Body signUpRequest: SignUpRequest): SignUpResponse

    @GET(GET_PROFILE_SELF_APPLICANT)
    suspend fun getProfileSelfApplicant(@Header("Authorization") token: String): GetProfileApplicantResponse

    @GET(GET_PROFILE_SELF_COMPANY)
    suspend fun getProfileSelfCompany(@Header("Authorization") token: String): GetProfileCompanyResponse

    @GET(GET_PROFILE_APPLICANT)
    suspend fun getProfileApplicantById(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): GetProfileApplicantResponse

    @GET(GET_PROFILE_COMPANY)
    suspend fun getProfileCompanyById(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): GetProfileCompanyResponse

    @Multipart
    @POST(UPLOAD_IMAGE)
    suspend fun uploadImage(
        @Part imageFile: MultipartBody.Part,
        @Header("Authorization") token: String
    ): UploadImageResponse

    @Multipart
    @POST(UPLOAD_IMAGE_COMPANY)
    suspend fun uploadImageCompany(
        @Part imageFile: MultipartBody.Part,
        @Header("Authorization") token: String
    ): UploadImageCompanyResponse

    @Multipart
    @POST(UPLOAD_CV)
    suspend fun uploadCv(
        @Header("Authorization") token: String,
        @Part pdfFile: MultipartBody.Part
    ): UploadCVResponse

    @PUT(EDIT_PROFILE_SELF_APPLICANT)
    suspend fun editProfileApplicant(
        @Header("Authorization") token: String,
        @Body editApplicantRequest: EditApplicantRequest
    ): EditApplicantResponse

    @PUT(EDIT_PROFILE_SELF_COMPANY)
    suspend fun editProfileCompany(
        @Header("Authorization") token: String,
        @Body editCompanyRequest: EditCompanyRequest
    ): EditCompanyResponse

    @GET(OFFER_ON_PROSES_HISTORY_COMPANY)
    suspend fun getOfferOnProsesHistoryCompany(@Header("Authorization") token: String): GetOfferHistoryCompany

    @GET(OFFER_ACCEPT_HISTORY_COMPANY)
    suspend fun getOfferAcceptHistoryCompany(@Header("Authorization") token: String): GetOfferHistoryCompany

    @GET(OFFER_REJECT_HISTORY_COMPANY)
    suspend fun getOfferRejectHistoryCompany(@Header("Authorization") token: String): GetOfferHistoryCompany


    @GET(OFFER_PENDING_HISTORY_APPLICANT)
    suspend fun getOfferPendingHistoryApplicant(@Header("Authorization") token: String): GetOfferHistoryApplicant

    @GET(OFFER_ON_PROSES_HISTORY_APPLICANT)
    suspend fun getOfferOnProsesHistoryApplicant(@Header("Authorization") token: String): GetOfferHistoryApplicant

    @GET(OFFER_ACCEPT_HISTORY_APPLICANT)
    suspend fun getOfferAcceptHistoryApplicant(@Header("Authorization") token: String): GetOfferHistoryApplicant

    @GET(OFFER_REJECT_HISTORY_APPLICANT)
    suspend fun getOfferRejectHistoryApplicant(@Header("Authorization") token: String): GetOfferHistoryApplicant

    @PUT(APPLICANT_ACCEPT_OFFER_BY_ID)
    suspend fun applicantAcceptOffer(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): OfferResponse

    @PUT(APPLICANT_REJECT_OFFER_BY_ID)
    suspend fun applicantRejectOffer(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): OfferResponse

    @PUT(COMPANY_ACCEPT_OFFER_BY_ID)
    suspend fun companyAcceptOffer(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): OfferResponse

    @PUT(COMPANY_REJECT_OFFER_BY_ID)
    suspend fun companyRejectOffer(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): OfferResponse

    @GET(APPLICANT_GET_LIST)
    suspend fun getApplicantList(
        @Header("Authorization") token: String,
        @Query("page") page: Int? = null,
        @Query("pageSize") size: Int? = null,
        @Query("search") search: String? = null
    ): ApplicantListResponse

    @POST(CREATE_OFFER_APPLICANT)
    suspend fun createOfferApplicant(
        @Header("Authorization") token: String,
        @Body createOfferRequest: CreateOfferRequest
    ): CreateOfferResponse

    @GET(GET_TOKEN_STREAM)
    suspend fun getTokenStream(
        @Header("Authorization") token: String,
    ): GetTokenResponse

    @POST(APPLICANT_RECOMMENDER_LIST)
    suspend fun getApplicantRecommender(
        @Header("Authorization") token: String,
        @Body applicantRecommender: ApplicantRecommenderRequest
    ): ApplicantRecommenderResponse


}