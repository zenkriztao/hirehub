package com.inkubasi.hirehub.di

import com.inkubasi.hirehub.presentation.applicant.dashboard.chat.ChatApplicantViewModel
import com.inkubasi.hirehub.presentation.applicant.dashboard.history.list.HistoryApplicantViewModel
import com.inkubasi.hirehub.presentation.applicant.dashboard.home.HomeApplicantViewModel
import com.inkubasi.hirehub.presentation.applicant.dashboard.home.detail.DetailCompanyViewModel
import com.inkubasi.hirehub.presentation.applicant.dashboard.profile.ProfileApplicantViewModel
import com.inkubasi.hirehub.presentation.applicant.dashboard.profile.detail.cv.UploadCvViewModel
import com.inkubasi.hirehub.presentation.applicant.dashboard.profile.menu.edit.EditProfileViewModel
import com.inkubasi.hirehub.presentation.applicant.dashboard.profile.menu.setting.SettingApplicantViewModel
import com.inkubasi.hirehub.presentation.applicant.login.LoginApplicantViewModel
import com.inkubasi.hirehub.presentation.applicant.register.RegisterApplicantViewModel
import com.inkubasi.hirehub.presentation.company.dashboard.chat.ChatCompanyViewModel
import com.inkubasi.hirehub.presentation.company.dashboard.history.detail.HistoryDetailViewModel
import com.inkubasi.hirehub.presentation.company.dashboard.history.list.HistoryCompanyViewModel
import com.inkubasi.hirehub.presentation.company.dashboard.home.HomeCompanyViewModel
import com.inkubasi.hirehub.presentation.company.dashboard.home.offer.CompanyOfferDetailViewModel
import com.inkubasi.hirehub.presentation.company.dashboard.profile.ProfileCompanyViewModel
import com.inkubasi.hirehub.presentation.company.dashboard.profile.menu.edit.EditProfileCompanyViewModel
import com.inkubasi.hirehub.presentation.company.dashboard.profile.menu.setting.SettingCompanyViewModel
import com.inkubasi.hirehub.presentation.company.login.LoginCompanyViewModel
import com.inkubasi.hirehub.presentation.company.register.RegisterCompanyViewModel
import com.inkubasi.hirehub.presentation.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        LoginApplicantViewModel(get(),get())
    }
    viewModel {
        RegisterApplicantViewModel(get())
    }
    viewModel {
        LoginCompanyViewModel(get(),get())
    }
    viewModel {
        RegisterCompanyViewModel(get())
    }
    viewModel {
        SplashViewModel(get())
    }
    viewModel {
        ProfileCompanyViewModel(get(),get())
    }
    viewModel {
        ProfileApplicantViewModel(get(),get())
    }
    viewModel {
        SettingApplicantViewModel(get())
    }
    viewModel {
        SettingCompanyViewModel(get())
    }
    viewModel {
        EditProfileViewModel(get(),get())
    }
    viewModel {
        EditProfileCompanyViewModel(get(),get())
    }
    viewModel {
        UploadCvViewModel(get(), get())
    }
    viewModel {
        HistoryApplicantViewModel(get(), get())
    }
    viewModel {
        HistoryCompanyViewModel(get(), get())
    }
    viewModel {
        HistoryDetailViewModel(get(), get())
    }
    viewModel {
        HomeCompanyViewModel(get(), get())
    }
    viewModel {
        CompanyOfferDetailViewModel(get(), get())
    }
    viewModel {
        HomeApplicantViewModel(get(), get())
    }
    viewModel {
        DetailCompanyViewModel(get(), get())
    }
    viewModel {
        ChatApplicantViewModel(get(), get())
    }
    viewModel {
        ChatCompanyViewModel(get(), get())
    }
}