package com.inkubasi.hirehub.coreapp.data.source

import com.inkubasi.hirehub.coreapp.data.source.remote.NewRemoteDataSource
import com.inkubasi.hirehub.coreapp.data.source.remote.network.ApiResponse
import com.inkubasi.hirehub.coreapp.data.source.remote.newresponse.*
import com.inkubasi.hirehub.coreapp.data.source.remote.request.ApplicantRecommenderRequest
import com.inkubasi.hirehub.coreapp.domain.repository.NewIHirehubRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import retrofit2.HttpException
import java.io.IOException

class NewHirehubRepository  constructor(
    private val remoteDataSource: NewRemoteDataSource,
) : NewIHirehubRepository {

    override fun registerApplicant(
        username: String,
        password: String
    ): Flow<Resource<SignUpResponse>>  = flow {
        try {
            emit(Resource.Loading())
            when(val register = remoteDataSource.registerApplicant(username, password).first()){
                is ApiResponse.Success -> {
                    emit(Resource.Success(register.data))
                }
                is ApiResponse.Empty -> emit(Resource.Error("Gagal registrasi"))
                is ApiResponse.Error -> emit(Resource.Error(register.errorMessage))
            }
        }catch (e: HttpException){
            emit(Resource.Error("Gagal Registrasi"))
        }catch (e: IOException){
            emit(Resource.Error("Gagal Registrasi"))
        }
    }

    override fun registerCompany(
        username: String, password: String
    ): Flow<Resource<SignUpResponse>> = flow {
        try {
            emit(Resource.Loading())
            when(val register = remoteDataSource.registerCompany(username, password).first()){
                is ApiResponse.Success -> {
                    emit(Resource.Success(register.data))
                }
                is ApiResponse.Empty -> emit(Resource.Error("Gagal registrasi"))
                is ApiResponse.Error -> emit(Resource.Error(register.errorMessage))
            }
        }catch (e: HttpException){
            emit(Resource.Error("Gagal registrasi"))
        }catch (e: IOException){
            emit(Resource.Error("Gagal registrasi"))
        }
    }

    override fun loginUser(username: String, password: String): Flow<Resource<SignInResponse>> = flow {
        try {
            emit(Resource.Loading())
            when(val login = remoteDataSource.loginUser(username, password).first()){
                is ApiResponse.Success -> {
                    emit(Resource.Success(login.data))
                }
                is ApiResponse.Empty -> emit(Resource.Error("Username/Password Salah"))
                is ApiResponse.Error -> emit(Resource.Error(login.errorMessage))
            }
        }catch (e: HttpException){
            emit(Resource.Error("Username/Password Salah"))
        }catch (e: IOException){
            emit(Resource.Error("Username/Password Salah"))
        }
    }

    override fun getProfileApplicantSelf(token: String): Flow<Resource<GetProfileApplicantResponse>> = flow {
        try {
            emit(Resource.Loading())
            when (val login = remoteDataSource.getProfileSefApplicant(token).first()) {
                is ApiResponse.Success -> {
                    emit(Resource.Success(login.data))
                }
                is ApiResponse.Empty -> emit(Resource.Error("Gagal Mendapatkan Profile"))
                is ApiResponse.Error -> emit(Resource.Error(login.errorMessage))
            }
        } catch (e: HttpException) {
            emit(Resource.Error("Gagal Mendapatkan Profile"))
        } catch (e: IOException) {
            emit(Resource.Error("Gagal Mendapatkan Profile"))
        }
    }

    override fun getProfileApplicantById(
        token: String,
        id: String
    ): Flow<Resource<GetProfileApplicantResponse>> = flow {
        try {
            emit(Resource.Loading())
            when (val login = remoteDataSource.getProfileApplicantById(token, id).first()) {
                is ApiResponse.Success -> {
                    emit(Resource.Success(login.data))
                }
                is ApiResponse.Empty -> emit(Resource.Error("Gagal Mendapatkan Profile"))
                is ApiResponse.Error -> emit(Resource.Error(login.errorMessage))
            }
        } catch (e: HttpException) {
            emit(Resource.Error("Gagal Mendapatkan Profile"))
        } catch (e: IOException) {
            emit(Resource.Error("Gagal Mendapatkan Profile"))
        }
    }

    override fun getProfileCompanyById(
        token: String,
        id: String
    ): Flow<Resource<GetProfileCompanyResponse>> = flow {
        try {
            emit(Resource.Loading())
            when (val login = remoteDataSource.getProfileCompanyById(token, id).first()) {
                is ApiResponse.Success -> {
                    emit(Resource.Success(login.data))
                }
                is ApiResponse.Empty -> emit(Resource.Error("Gagal Mendapatkan Profile"))
                is ApiResponse.Error -> emit(Resource.Error(login.errorMessage))
            }
        } catch (e: HttpException) {
            emit(Resource.Error("Gagal Mendapatkan Profile"))
        } catch (e: IOException) {
            emit(Resource.Error("Gagal Mendapatkan Profile"))
        }
    }

    override fun getProfileCompanySelf(token: String): Flow<Resource<GetProfileCompanyResponse>> =
        flow {
            try {
                emit(Resource.Loading())
                when (val login = remoteDataSource.getProfileSefCompany(token).first()) {
                    is ApiResponse.Success -> {
                        emit(Resource.Success(login.data))
                    }
                    is ApiResponse.Empty -> emit(Resource.Error("Gagal Mendapatkan Profile"))
                    is ApiResponse.Error -> emit(Resource.Error(login.errorMessage))
                }
        }catch (e: HttpException){
            emit(Resource.Error("Gagal Mendapatkan Profile"))
        }catch (e: IOException){
            emit(Resource.Error("Gagal Mendapatkan Profile"))
        }
    }

    override fun uploadImage(token: String, imageFile: MultipartBody.Part): Flow<Resource<UploadImageResponse>> = flow {
        try {
            emit(Resource.Loading())
            when(val uploadImage = remoteDataSource.uploadImage(token, imageFile).first()){
                is ApiResponse.Success -> {
                    emit(Resource.Success(uploadImage.data))
                }
                is ApiResponse.Empty -> emit(Resource.Error("Gagal Upload Foto Profile"))
                is ApiResponse.Error -> emit(Resource.Error(uploadImage.errorMessage))
            }
        }catch (e: HttpException){
            emit(Resource.Error("Gagal Upload Foto Profile"))
        }catch (e: IOException){
            emit(Resource.Error("Gagal Upload Foto Profile"))
        }
    }

    override fun uploadImageCompany(
        token: String,
        imageFile: MultipartBody.Part
    ): Flow<Resource<UploadImageCompanyResponse>> = flow {
        try {
            emit(Resource.Loading())
            when(val uploadImage = remoteDataSource.uploadImageCompany(token, imageFile).first()){
                is ApiResponse.Success -> {
                    emit(Resource.Success(uploadImage.data))
                }
                is ApiResponse.Empty -> emit(Resource.Error("Gagal Upload Foto Profile"))
                is ApiResponse.Error -> emit(Resource.Error(uploadImage.errorMessage))
            }
        }catch (e: HttpException){
            emit(Resource.Error("Gagal Upload Foto Profile"))
        }catch (e: IOException){
            emit(Resource.Error("Gagal Upload Foto Profile"))
        }
    }

    override fun uploadCv(token: String, pdfFile: MultipartBody.Part): Flow<Resource<UploadCVResponse>> = flow {
        try {
            emit(Resource.Loading())
            when(val uploadCv = remoteDataSource.uploadCv(token, pdfFile).first()){
                is ApiResponse.Success -> {
                    emit(Resource.Success(uploadCv.data))
                }
                is ApiResponse.Empty -> emit(Resource.Error("Gagal Upload CV"))
                is ApiResponse.Error -> emit(Resource.Error(uploadCv.errorMessage))
            }
        }catch (e: HttpException){
            emit(Resource.Error("Gagal Upload CV"))
        }catch (e: IOException){
            emit(Resource.Error("Gagal Upload CV"))
        }
    }

    override fun editProfileCompany(token: String, name: String, location: String, office: String, webUrl: String,
        industry: String, employee: String, description: String): Flow<Resource<EditCompanyResponse>>  = flow {
        try {
            emit(Resource.Loading())
            when(val editCompany = remoteDataSource.editProfileCompany(token,
                name, location, office, webUrl, industry, employee, description).first()){
                is ApiResponse.Success -> {
                    emit(Resource.Success(editCompany.data))
                }
                is ApiResponse.Empty -> emit(Resource.Error("Gagal Update Company"))
                is ApiResponse.Error -> emit(Resource.Error(editCompany.errorMessage))
            }
        }catch (e: HttpException){
            emit(Resource.Error("Gagal Update Company"))
        }catch (e: IOException){
            emit(Resource.Error("Gagal Update Company"))
        }
    }

    override fun editProfileApplicant(token: String, name: String, email: String, dateOfBirth: String, language: List<String>,
        field: String, skills: List<String>, salaryMin: Int, location: String,institution: String , degree: String, phone: String, summary: String
    ): Flow<Resource<EditApplicantResponse>> = flow {
        try {
            emit(Resource.Loading())
            when(val editCompany = remoteDataSource.editProfileApplicant(token, name, email, dateOfBirth, language, field, skills,
                salaryMin, location,institution, degree, phone, summary).first()){
                is ApiResponse.Success -> {
                    emit(Resource.Success(editCompany.data))
                }
                is ApiResponse.Empty -> emit(Resource.Error("Gagal Update Profile"))
                is ApiResponse.Error -> emit(Resource.Error(editCompany.errorMessage))
            }
        }catch (e: HttpException){
            emit(Resource.Error("Gagal Update Profile"))
        }catch (e: IOException){
            emit(Resource.Error("Gagal Update Profile"))
        }
    }

    override fun getOfferOnProsesHistoryCompany(token: String): Flow<Resource<GetOfferHistoryCompany>> = flow {
        try {
            emit(Resource.Loading())
            when(val api = remoteDataSource.getOfferOnProsesHistoryCompany(token).first()){
                is ApiResponse.Success -> {
                    emit(Resource.Success(api.data))
                }
                is ApiResponse.Empty -> emit(Resource.Error("Tidak ada riwayat"))
                is ApiResponse.Error -> emit(Resource.Error(api.errorMessage))
            }
        }catch (e: HttpException){
            emit(Resource.Error("Tidak ada riwayat"))
        }catch (e: IOException){
            emit(Resource.Error("Tidak ada riwayat"))
        }
    }

    override fun getOfferAcceptHistoryCompany(token: String): Flow<Resource<GetOfferHistoryCompany>> = flow {
        try {
            emit(Resource.Loading())
            when(val api = remoteDataSource.getOfferAcceptHistoryCompany(token).first()){
                is ApiResponse.Success -> {
                    emit(Resource.Success(api.data))
                }
                is ApiResponse.Empty -> emit(Resource.Error("Tidak ada riwayat"))
                is ApiResponse.Error -> emit(Resource.Error(api.errorMessage))
            }
        }catch (e: HttpException){
            emit(Resource.Error("Tidak ada riwayat"))
        }catch (e: IOException){
            emit(Resource.Error("Tidak ada riwayat"))
        }
    }

    override fun getOfferRejectHistoryCompany(token: String): Flow<Resource<GetOfferHistoryCompany>>  = flow {
        try {
            emit(Resource.Loading())
            when(val api = remoteDataSource.getOfferRejectHistoryCompany(token).first()){
                is ApiResponse.Success -> {
                    emit(Resource.Success(api.data))
                }
                is ApiResponse.Empty -> emit(Resource.Error("Tidak ada riwayat"))
                is ApiResponse.Error -> emit(Resource.Error(api.errorMessage))
            }
        }catch (e: HttpException){
            emit(Resource.Error("Tidak ada riwayat"))
        }catch (e: IOException){
            emit(Resource.Error("Tidak ada riwayat"))
        }
    }
    override fun getOfferPendingHistoryApplicant(token: String): Flow<Resource<GetOfferHistoryApplicant>> = flow {
        try {
            emit(Resource.Loading())
            when(val api = remoteDataSource.getOfferPendingHistoryApplicant(token).first()){
                is ApiResponse.Success -> {
                    emit(Resource.Success(api.data))
                }
                is ApiResponse.Empty -> emit(Resource.Error("Tidak ada riwayat"))
                is ApiResponse.Error -> emit(Resource.Error(api.errorMessage))
            }
        }catch (e: HttpException){
            emit(Resource.Error("Tidak ada riwayat"))
        }catch (e: IOException){
            emit(Resource.Error("Tidak ada riwayat"))
        }
    }

    override fun getOfferAcceptHistoryApplicant(token: String): Flow<Resource<GetOfferHistoryApplicant>> = flow {
        try {
            emit(Resource.Loading())
            when(val api = remoteDataSource.getOfferAcceptHistoryApplicant(token).first()){
                is ApiResponse.Success -> {
                    emit(Resource.Success(api.data))
                }
                is ApiResponse.Empty -> emit(Resource.Error("Tidak ada riwayat"))
                is ApiResponse.Error -> emit(Resource.Error(api.errorMessage))
            }
        }catch (e: HttpException){
            emit(Resource.Error("Tidak ada riwayat"))
        }catch (e: IOException){
            emit(Resource.Error("Tidak ada riwayat"))
        }
    }

    override fun getOfferOnProsesHistoryApplicant(token: String): Flow<Resource<GetOfferHistoryApplicant>> = flow {
        try {
            emit(Resource.Loading())
            when(val api = remoteDataSource.getOfferOnProsesHistoryApplicant(token).first()){
                is ApiResponse.Success -> {
                    emit(Resource.Success(api.data))
                }
                is ApiResponse.Empty -> emit(Resource.Error("Tidak ada riwayat"))
                is ApiResponse.Error -> emit(Resource.Error(api.errorMessage))
            }
        }catch (e: HttpException){
            emit(Resource.Error("Tidak ada riwayat"))
        }catch (e: IOException){
            emit(Resource.Error("Tidak ada riwayat"))
        }
    }

    override fun getOfferRejectHistoryApplicant(token: String): Flow<Resource<GetOfferHistoryApplicant>> = flow {
        try {
            emit(Resource.Loading())
            when(val api = remoteDataSource.getOfferRejectHistoryApplicant(token).first()){
                is ApiResponse.Success -> {
                    emit(Resource.Success(api.data))
                }
                is ApiResponse.Empty -> emit(Resource.Error("Tidak ada riwayat"))
                is ApiResponse.Error -> emit(Resource.Error(api.errorMessage))
            }
        } catch (e: HttpException) {
            emit(Resource.Error("Tidak ada riwayat"))
        } catch (e: IOException) {
            emit(Resource.Error("Tidak ada riwayat"))
        }
    }

    override fun applicantAcceptOffer(token: String, id: String): Flow<Resource<OfferResponse>> =
        flow {
            try {
                emit(Resource.Loading())
                when (val api = remoteDataSource.applicantAcceptOffer(token, id).first()) {
                    is ApiResponse.Success -> {
                        emit(Resource.Success(api.data))
                    }
                    is ApiResponse.Empty -> emit(Resource.Error("Tidak berhasil update offer"))
                    is ApiResponse.Error -> emit(Resource.Error(api.errorMessage))
                }
            } catch (e: HttpException) {
                emit(Resource.Error("Tidak berhasil update offer"))
            } catch (e: IOException) {
                emit(Resource.Error("Tidak berhasil update offer"))
            }
        }

    override fun applicantRejectOffer(token: String, id: String): Flow<Resource<OfferResponse>> =
        flow {
            try {
                emit(Resource.Loading())
                when (val api = remoteDataSource.applicantRejectOffer(token, id).first()) {
                    is ApiResponse.Success -> {
                        emit(Resource.Success(api.data))
                    }
                    is ApiResponse.Empty -> emit(Resource.Error("Tidak berhasil update offer"))
                    is ApiResponse.Error -> emit(Resource.Error(api.errorMessage))
                }
            } catch (e: HttpException) {
                emit(Resource.Error("Tidak berhasil update offer"))
            } catch (e: IOException) {
                emit(Resource.Error("Tidak berhasil update offer"))
            }
        }

    override fun companyAcceptOffer(token: String, id: String): Flow<Resource<OfferResponse>> =
        flow {
            try {
                emit(Resource.Loading())
                when (val api = remoteDataSource.companyAcceptOffer(token, id).first()) {
                    is ApiResponse.Success -> {
                        emit(Resource.Success(api.data))
                    }
                    is ApiResponse.Empty -> emit(Resource.Error("Tidak berhasil update offer"))
                    is ApiResponse.Error -> emit(Resource.Error(api.errorMessage))
                }
            } catch (e: HttpException) {
                emit(Resource.Error("Tidak berhasil update offer"))
            } catch (e: IOException) {
                emit(Resource.Error("Tidak berhasil update offer"))
            }
        }

    override fun companyRejectOffer(token: String, id: String): Flow<Resource<OfferResponse>> =
        flow {
            try {
                emit(Resource.Loading())
                when (val api = remoteDataSource.companyRejectOffer(token, id).first()) {
                    is ApiResponse.Success -> {
                        emit(Resource.Success(api.data))
                    }
                    is ApiResponse.Empty -> emit(Resource.Error("Tidak berhasil update offer"))
                    is ApiResponse.Error -> emit(Resource.Error(api.errorMessage))
                }
            } catch (e: HttpException) {
                emit(Resource.Error("Tidak berhasil update offer"))
            } catch (e: IOException) {
                emit(Resource.Error("Tidak berhasil update offer"))
            }
        }

    override fun getApplicantList(
        token: String,
        currentPage: Int,
        search: String
    ): Flow<Resource<ApplicantListResponse>> =
        flow {
            try {
                emit(Resource.Loading())
                when (val api = remoteDataSource.getApplicantList(
                    token,
                    currentPage,
                    BLOG_LIMIT_PER_PAGE,
                    search
                ).first()) {
                    is ApiResponse.Success -> {
                        emit(Resource.Success(api.data))
                    }
                    is ApiResponse.Empty -> emit(Resource.Error("Applicant tidak ditemukan"))
                    is ApiResponse.Error -> emit(Resource.Error(api.errorMessage))
                }
            } catch (e: HttpException) {
                emit(Resource.Error("Applicant tidak ditemukan"))
            } catch (e: IOException) {
                emit(Resource.Error("Applicant tidak ditemukan"))
            }
        }

    override fun createOfferApplicant(
        token: String,
        applicantId: String
    ): Flow<Resource<CreateOfferResponse>> = flow {
        try {
            emit(Resource.Loading())
            when (val api = remoteDataSource.createOfferApplicant(token, applicantId).first()) {
                is ApiResponse.Success -> {
                    emit(Resource.Success(api.data))
                }
                is ApiResponse.Empty -> emit(Resource.Error("Offer tidak ditemukan"))
                is ApiResponse.Error -> emit(Resource.Error(api.errorMessage))
            }
        } catch (e: HttpException) {
            emit(Resource.Error("Offer tidak ditemukan"))
        } catch (e: IOException) {
            emit(Resource.Error("Offer tidak ditemukan"))
        }
    }

    override fun getStreamToken(token: String): Flow<Resource<GetTokenResponse>> = flow {
        try {
            emit(Resource.Loading())
            when (val api = remoteDataSource.getStreamToken(token).first()) {
                is ApiResponse.Success -> {
                    emit(Resource.Success(api.data))
                }
                is ApiResponse.Empty -> emit(Resource.Error("Token tidak ditemukan"))
                is ApiResponse.Error -> emit(Resource.Error(api.errorMessage))
            }
        } catch (e: HttpException) {
            emit(Resource.Error("Token tidak ditemukan"))
        } catch (e: IOException) {
            emit(Resource.Error("Token tidak ditemukan"))
        }
    }

    override fun getApplicantRecommendation(
        token: String,
        applicantRecommenderRequest: ApplicantRecommenderRequest
    ): Flow<Resource<ApplicantRecommenderResponse>> = flow {
        try {
            emit(Resource.Loading())
            when (val api =
                remoteDataSource.getApplicantRecommendation(token, applicantRecommenderRequest)
                    .first()) {
                is ApiResponse.Success -> {
                    emit(Resource.Success(api.data))
                }
                is ApiResponse.Empty -> emit(Resource.Error("Applicant tidak ditemukan"))
                is ApiResponse.Error -> emit(Resource.Error(api.errorMessage))
            }
        } catch (e: HttpException) {
            emit(Resource.Error("Applicant tidak ditemukan"))
        } catch (e: IOException) {
            emit(Resource.Error("Applicant tidak ditemukan"))
        }
    }


    companion object {
        const val BLOG_LIMIT_PER_PAGE = 10
    }


}