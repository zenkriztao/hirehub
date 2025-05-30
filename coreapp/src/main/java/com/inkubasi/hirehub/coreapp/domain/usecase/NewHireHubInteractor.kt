package com.inkubasi.hirehub.coreapp.domain.usecase

import com.inkubasi.hirehub.coreapp.data.source.Resource
import com.inkubasi.hirehub.coreapp.data.source.remote.newresponse.*
import com.inkubasi.hirehub.coreapp.data.source.remote.request.ApplicantRecommenderRequest
import com.inkubasi.hirehub.coreapp.domain.repository.NewIHirehubRepository
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

class NewHireHubInteractor  constructor(
    private val repository: NewIHirehubRepository
) : NewHirehubUseCase {


    override fun registerApplicant(username: String, password: String
    ): Flow<Resource<SignUpResponse>> {
        return repository.registerApplicant(username,password)
    }

    override fun registerCompany(username: String, password: String
    ): Flow<Resource<SignUpResponse>> {
        return repository.registerCompany(username,password)
    }

    override fun loginUser(
        username: String, password: String
    ): Flow<Resource<SignInResponse>> {
        return repository.loginUser(username, password)
    }

    override fun getProfileApplicantSelf(token: String): Flow<Resource<GetProfileApplicantResponse>> {
        return repository.getProfileApplicantSelf(token)
    }

    override fun getProfileApplicantById(
        token: String,
        id: String
    ): Flow<Resource<GetProfileApplicantResponse>> {
        return repository.getProfileApplicantById(token, id)
    }

    override fun getProfileCompanyById(
        token: String,
        id: String
    ): Flow<Resource<GetProfileCompanyResponse>> {
        return repository.getProfileCompanyById(token, id)
    }

    override fun getProfileCompanySelf(token: String): Flow<Resource<GetProfileCompanyResponse>> {
        return repository.getProfileCompanySelf(token)
    }

    override fun uploadImage(
        token: String,
        imageFile: MultipartBody.Part
    ): Flow<Resource<UploadImageResponse>> {
        return repository.uploadImage(token, imageFile)
    }

    override fun uploadImageCompany(
        token: String,
        imageFile: MultipartBody.Part
    ): Flow<Resource<UploadImageCompanyResponse>> {
        return repository.uploadImageCompany(token, imageFile)
    }

    override fun uploadCv(token: String, pdfFile: MultipartBody.Part): Flow<Resource<UploadCVResponse>> {
        return repository.uploadCv(token, pdfFile)
    }

    override fun editProfileCompany(token: String, name: String, location: String, office: String, webUrl: String,
        industry: String, employee: String, description: String): Flow<Resource<EditCompanyResponse>> {
        return repository.editProfileCompany(token, name, location, office, webUrl, industry, employee, description)
    }

    override fun editProfileApplicant(token: String, name: String, email: String, dateOfBirth: String, language: List<String>,
        field: String, skills: List<String>, salaryMin: Int, location: String, institution: String , degree: String, phone: String, summary: String
    ): Flow<Resource<EditApplicantResponse>> {
        return repository.editProfileApplicant(token, name, email, dateOfBirth, language, field, skills, salaryMin, location, institution, degree, phone, summary)
    }

    override fun getOfferOnProsesHistoryCompany(token: String): Flow<Resource<GetOfferHistoryCompany>> {
        return repository.getOfferOnProsesHistoryCompany(token)
    }

    override fun getOfferAcceptHistoryCompany(token: String): Flow<Resource<GetOfferHistoryCompany>> {
        return repository.getOfferAcceptHistoryCompany(token)
    }

    override fun getOfferRejectHistoryCompany(token: String): Flow<Resource<GetOfferHistoryCompany>> {
        return repository.getOfferRejectHistoryCompany(token)
    }

    override fun getOfferPendingHistoryApplicant(token: String): Flow<Resource<GetOfferHistoryApplicant>> {
        return repository.getOfferPendingHistoryApplicant(token)
    }

    override fun getOfferAcceptHistoryApplicant(token: String): Flow<Resource<GetOfferHistoryApplicant>> {
        return repository.getOfferAcceptHistoryApplicant(token)
    }

    override fun getOfferOnProsesHistoryApplicant(token: String): Flow<Resource<GetOfferHistoryApplicant>> {
        return repository.getOfferOnProsesHistoryApplicant(token)
    }

    override fun getOfferRejectHistoryApplicant(token: String): Flow<Resource<GetOfferHistoryApplicant>> {
        return repository.getOfferRejectHistoryApplicant(token)
    }

    override fun applicantAcceptOffer(token: String, id: String): Flow<Resource<OfferResponse>> {
        return repository.applicantAcceptOffer(token, id)
    }

    override fun applicantRejectOffer(token: String, id: String): Flow<Resource<OfferResponse>> {
        return repository.applicantRejectOffer(token, id)
    }

    override fun companyAcceptOffer(token: String, id: String): Flow<Resource<OfferResponse>> {
        return repository.companyAcceptOffer(token, id)
    }

    override fun companyRejectOffer(token: String, id: String): Flow<Resource<OfferResponse>> {
        return repository.companyRejectOffer(token, id)
    }

    override fun getApplicantList(
        token: String,
        currentPage: Int,
        search: String
    ): Flow<Resource<ApplicantListResponse>> {
        return repository.getApplicantList(token, currentPage, search)
    }

    override fun createOfferApplicant(
        token: String,
        applicantId: String
    ): Flow<Resource<CreateOfferResponse>> {
        return repository.createOfferApplicant(token, applicantId)
    }

    override fun getStreamToken(token: String): Flow<Resource<GetTokenResponse>> {
        return repository.getStreamToken(token)
    }

    override fun getApplicantRecommendation(
        token: String,
        applicantRecommenderRequest: ApplicantRecommenderRequest
    ): Flow<Resource<ApplicantRecommenderResponse>> {
        return repository.getApplicantRecommendation(token, applicantRecommenderRequest)
    }


}