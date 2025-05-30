package com.inkubasi.hirehub.presentation.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.inkubasi.hirehub.R
import com.inkubasi.hirehub.presentation.applicant.dashboard.MainApplicantActivity
import com.inkubasi.hirehub.presentation.company.dashboard.MainCompanyActivity

class SplashScreenActivity : AppCompatActivity() {

    private val viewModel : SplashViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        supportActionBar?.hide()


        Handler(Looper.getMainLooper()).postDelayed({
            viewModel.getUser().observe(this){
                if (it.isLogin){
                    if (it.isApplicant){
                        val splashIntent = Intent(this, MainApplicantActivity::class.java)
                        startActivity(splashIntent)
                        finish()
                    }else{
                        val splashIntent = Intent(this, MainCompanyActivity::class.java)
                        startActivity(splashIntent)
                        finish()
                    }
                }else{
                    val splashIntent = Intent(this, LoginOptionActivity::class.java)
                    startActivity(splashIntent)
                    finish()
                }
            }
        }, TIME)
    }

    companion object {
        const val TIME = 2000L
    }
}