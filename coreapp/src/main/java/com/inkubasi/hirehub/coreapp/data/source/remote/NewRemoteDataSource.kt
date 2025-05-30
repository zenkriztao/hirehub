package com.inkubasi.hirehub.coreapp.data.source.remote

import com.inkubasi.hirehub.coreapp.data.source.remote.network.ApiResponse
import com.inkubasi.hirehub.coreapp.data.source.remote.network.NewApiService
import com.inkubasi.hirehub.coreapp.data.source.remote.newresponse.*
import com.inkubasi.hirehub.coreapp.data.source.remote.request.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MultipartBody

class NewRemoteDataSource constructor(
    private val apiService: NewApiService
) {

    suspend fun registerApplicant(username : String,  password: String): Flow<ApiResponse<SignUpResponse>> {
        return flow {
            val response = apiService.signUp(SignUpRequest(username, password, "applicant"))
            try {
                if (response.success == true){
                    emit(ApiResponse.Success(response))
                }else{
                    emit(ApiResponse.Error(response.message.orEmpty()))
                }
            }catch (e:Exception){
                emit(ApiResponse.Error(response.message.orEmpty()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun registerCompany(username : String,  password: String): Flow<ApiResponse<SignUpResponse>> {
        return flow {
            try {
                val response = apiService.signUp(SignUpRequest(username, password, "company"))
                if (response.success == true){
                    emit(ApiResponse.Success(response))
                }else{
                    emit(ApiResponse.Error(response.message.orEmpty()))
                }
            }catch (e:Exception){
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun loginUser(username : String,  password: String): Flow<ApiResponse<SignInResponse>> {
        return flow {
            val response = apiService.signIn(SignInRequest(username, password))
            try {
                if (response.success == true) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Error(response.message.orEmpty()))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(response.message.orEmpty()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getProfileSefApplicant(token: String): Flow<ApiResponse<GetProfileApplicantResponse>> {
        return flow {
            try {
                val response = apiService.getProfileSelfApplicant(token)
                if (response.success == true) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Error(response.message ?: "Error getting data"))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getProfileApplicantById(
        token: String,
        id: String
    ): Flow<ApiResponse<GetProfileApplicantResponse>> {
        return flow {
            try {
                val response = apiService.getProfileApplicantById(token, id)
                if (response.success == true) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Error(response.message ?: "Error getting data"))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getProfileCompanyById(
        token: String,
        id: String
    ): Flow<ApiResponse<GetProfileCompanyResponse>> {
        return flow {
            try {
                val response = apiService.getProfileCompanyById(token, id)
                if (response.success == true) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Error(response.message ?: "Error getting data"))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getProfileSefCompany(token: String): Flow<ApiResponse<GetProfileCompanyResponse>> {
        return flow {
            try {
                val response = apiService.getProfileSelfCompany(token)
                if (response.success == true) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Error(response.message.orEmpty()))
                }
            }catch (e:Exception){
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun uploadImage(token : String, imageFile : MultipartBody.Part): Flow<ApiResponse<UploadImageResponse>> {
        return flow {
            try {
                val response = apiService.uploadImage( imageFile, "Bearer $token",)
                if (response.success == true){
                    emit(ApiResponse.Success(response))
                }else{
                    emit(ApiResponse.Error(response.message.orEmpty()))
                }
            }catch (e:Exception){
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun uploadImageCompany(token : String, imageFile : MultipartBody.Part): Flow<ApiResponse<UploadImageCompanyResponse>> {
        return flow {
            try {
                val response = apiService.uploadImageCompany( imageFile, "Bearer $token",)
                if (response.success == true){
                    emit(ApiResponse.Success(response))
                }else{
                    emit(ApiResponse.Error(response.message.orEmpty()))
                }
            }catch (e:Exception){
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun uploadCv(token : String, pdfFile : MultipartBody.Part): Flow<ApiResponse<UploadCVResponse>> {
        return flow {
            try {
                val response = apiService.uploadCv(token, pdfFile)
                if (response.success == true){
                    emit(ApiResponse.Success(response))
                }else{
                    emit(ApiResponse.Error(response.message.orEmpty()))
                }
            }catch (e:Exception){
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun editProfileCompany(token: String, name: String, location: String, office: String, webUrl: String, industry: String,
                                    employee: String, description: String) : Flow<ApiResponse<EditCompanyResponse>> {
        return flow {
            try {
                val response = apiService.editProfileCompany(token,
                    EditCompanyRequest(name, location, office, webUrl, industry, employee, description))
                if (response.success == true){
                    emit(ApiResponse.Success(response))
                }else{
                    emit(ApiResponse.Error(response.message.orEmpty()))
                }
            }catch (e: Exception){
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun editProfileApplicant(token: String, name: String, email: String, dateOfBirth: String, language: List<String>, field: String,
                                      skills: List<String>, salaryMin: Int, location: String,
                                     institution: String , degree: String, phone: String, summary: String ) : Flow<ApiResponse<EditApplicantResponse>> {
        return flow {
            try {
                val response = apiService.editProfileApplicant(token, EditApplicantRequest(name, email, dateOfBirth, language,
                    field, skills, salaryMin, location,institution, degree, phone, summary))
                if (response.success){
                    emit(ApiResponse.Success(response))
                }else{
                    emit(ApiResponse.Error(response.message))
                }
            }catch (e: Exception){
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getOfferOnProsesHistoryCompany(token: String) : Flow<ApiResponse<GetOfferHistoryCompany>> {
        return flow {
            try {
                val response = apiService.getOfferOnProsesHistoryCompany(token)
                if (response.success){
                    emit(ApiResponse.Success(response))
                }else{
                    emit(ApiResponse.Error(response.message))
                }
            }catch (e: Exception){
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getOfferAcceptHistoryCompany(token: String) : Flow<ApiResponse<GetOfferHistoryCompany>> {
        return flow {
            try {
                val response = apiService.getOfferAcceptHistoryCompany(token)
                if (response.success){
                    emit(ApiResponse.Success(response))
                }else{
                    emit(ApiResponse.Error(response.message))
                }
            }catch (e: Exception){
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getOfferRejectHistoryCompany(token: String) : Flow<ApiResponse<GetOfferHistoryCompany>> {
        return flow {
            try {
                val response = apiService.getOfferRejectHistoryCompany(token)
                if (response.success){
                    emit(ApiResponse.Success(response))
                }else{
                    emit(ApiResponse.Error(response.message))
                }
            }catch (e: Exception){
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getOfferPendingHistoryApplicant(token: String) : Flow<ApiResponse<GetOfferHistoryApplicant>> {
        return flow {
            try {
                val response = apiService.getOfferPendingHistoryApplicant(token)
                if (response.success){
                    emit(ApiResponse.Success(response))
                }else{
                    emit(ApiResponse.Error(response.message))
                }
            }catch (e: Exception){
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getOfferAcceptHistoryApplicant(token: String) : Flow<ApiResponse<GetOfferHistoryApplicant>> {
        return flow {
            try {
                val response = apiService.getOfferAcceptHistoryApplicant(token)
                if (response.success){
                    emit(ApiResponse.Success(response))
                }else{
                    emit(ApiResponse.Error(response.message))
                }
            }catch (e: Exception){
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getOfferOnProsesHistoryApplicant(token: String) : Flow<ApiResponse<GetOfferHistoryApplicant>> {
        return flow {
            try {
                val response = apiService.getOfferOnProsesHistoryApplicant(token)
                if (response.success){
                    emit(ApiResponse.Success(response))
                }else{
                    emit(ApiResponse.Error(response.message))
                }
            }catch (e: Exception){
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }


    suspend fun getOfferRejectHistoryApplicant(token: String) : Flow<ApiResponse<GetOfferHistoryApplicant>> {
        return flow {
            try {
                val response = apiService.getOfferRejectHistoryApplicant(token)
                if (response.success) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Error(response.message))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun applicantAcceptOffer(token: String, id: String): Flow<ApiResponse<OfferResponse>> {
        return flow {
            try {
                val response = apiService.applicantAcceptOffer(token, id)
                if (response.success) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Error(response.message))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun applicantRejectOffer(token: String, id: String): Flow<ApiResponse<OfferResponse>> {
        return flow {
            try {
                val response = apiService.applicantRejectOffer(token, id)
                if (response.success) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Error(response.message))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun companyAcceptOffer(token: String, id: String): Flow<ApiResponse<OfferResponse>> {
        return flow {
            try {
                val response = apiService.companyAcceptOffer(token, id)
                if (response.success) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Error(response.message))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun companyRejectOffer(token: String, id: String): Flow<ApiResponse<OfferResponse>> {
        return flow {
            try {
                val response = apiService.companyRejectOffer(token, id)
                if (response.success) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Error(response.message))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getApplicantList(
        token: String,
        page: Int,
        pageSize: Int,
        search: String
    ): Flow<ApiResponse<ApplicantListResponse>> {
        return flow {
            try {
                val response = apiService.getApplicantList(token, page, pageSize, search)
                if (response.success) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Error(response.message))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun createOfferApplicant(
        token: String,
        applicantId: String
    ): Flow<ApiResponse<CreateOfferResponse>> {
        return flow {
            val response = apiService.createOfferApplicant(token, CreateOfferRequest(applicantId))
            try {
                if (response.success) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Error(response.message.orEmpty()))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(response.message.orEmpty()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getStreamToken(token: String): Flow<ApiResponse<GetTokenResponse>> {
        return flow {
            val response = apiService.getTokenStream(token)
            try {
                if (response.success) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Error(response.message))
                }
            } catch (e: java.lang.Exception) {
                emit(ApiResponse.Error(response.message))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getApplicantRecommendation(
        token: String,
        applicantRecommenderRequest: ApplicantRecommenderRequest
    ): Flow<ApiResponse<ApplicantRecommenderResponse>> {
        return flow {
            val response = apiService.getApplicantRecommender(token, applicantRecommenderRequest)
            try {
                if (response.success) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Error(response.message))
                }
            } catch (e: java.lang.Exception) {
                emit(ApiResponse.Error(response.message))
            }
        }.flowOn(Dispatchers.IO)
    }

}