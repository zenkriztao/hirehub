package com.inkubasi.hirehub.presentation.applicant.dashboard.profile.menu.setting

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.inkubasi.hirehub.databinding.ActivitySettingsBinding
import com.inkubasi.hirehub.presentation.splash.LoginOptionActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.inkubasi.hirehub.coreapp.utils.bottomsheet.SignOutConfirmationBottomSheet


class SettingsApplicantActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySettingsBinding
    private val settingApplicantViewModel : SettingApplicantViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.buttonLogout.setOnClickListener {
            showSignOutBottomSheet()
        }
    }

    private fun showSignOutBottomSheet() {
        SignOutConfirmationBottomSheet.newInstance{
            settingApplicantViewModel.logout()
            val intent = Intent(this, LoginOptionActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }.show(supportFragmentManager, SignOutConfirmationBottomSheet.TAG)
    }
}