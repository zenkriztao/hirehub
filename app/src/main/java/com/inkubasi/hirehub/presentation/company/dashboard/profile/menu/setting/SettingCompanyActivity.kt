package com.inkubasi.hirehub.presentation.company.dashboard.profile.menu.setting

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.inkubasi.hirehub.databinding.ActivitySettingCompanyBinding
import com.inkubasi.hirehub.presentation.splash.LoginOptionActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.inkubasi.hirehub.coreapp.utils.bottomsheet.SignOutConfirmationBottomSheet

class SettingCompanyActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySettingCompanyBinding
    private val settingCompanyViewModel: SettingCompanyViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivitySettingCompanyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.logout.setOnClickListener {
            showSignOutBottomSheet()
        }
    }

    private fun showSignOutBottomSheet() {
        SignOutConfirmationBottomSheet.newInstance{
            settingCompanyViewModel.logout()
            val intent = Intent(this, LoginOptionActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }.show(supportFragmentManager, SignOutConfirmationBottomSheet.TAG)
    }
}