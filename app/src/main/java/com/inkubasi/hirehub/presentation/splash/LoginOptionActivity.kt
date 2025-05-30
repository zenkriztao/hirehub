package com.inkubasi.hirehub.presentation.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.inkubasi.hirehub.databinding.ActivityLoginOptionBinding
import com.inkubasi.hirehub.presentation.applicant.login.LoginApplicantActivity
import com.inkubasi.hirehub.presentation.company.login.LoginCompanyActivity

class LoginOptionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginOptionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginOptionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.cvApplicant.setOnClickListener {
            Intent(this, LoginApplicantActivity::class.java).also {
                startActivity(it)
            }
        }

        binding.cvCompany.setOnClickListener {
            Intent(this, LoginCompanyActivity::class.java).also {
                startActivity(it)
            }
        }
    }
}