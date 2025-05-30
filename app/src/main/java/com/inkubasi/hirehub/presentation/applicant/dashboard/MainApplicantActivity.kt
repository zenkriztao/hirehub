package com.inkubasi.hirehub.presentation.applicant.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.inkubasi.hirehub.R
import com.inkubasi.hirehub.presentation.applicant.dashboard.chat.ChatApplicantFragment
import com.inkubasi.hirehub.presentation.applicant.dashboard.home.HomeApplicantFragment
import com.inkubasi.hirehub.presentation.applicant.dashboard.history.HistoryApplicantFragment
import com.inkubasi.hirehub.presentation.applicant.dashboard.profile.ProfileApplicantFragment

class MainApplicantActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_applicant)
        supportActionBar?.hide()
        bottomNavigationView = findViewById(R.id.bottom_nav)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            item ->
            var selectedFragment: Fragment? = null

            when (item.itemId){
                R.id.navigation_dashboard_aplc -> selectedFragment = HomeApplicantFragment()
                R.id.navigation_chat_aplc -> selectedFragment = ChatApplicantFragment()
                R.id.navigation_history_aplc -> selectedFragment = HistoryApplicantFragment()
                R.id.navigation_profile_applc -> selectedFragment = ProfileApplicantFragment()
            }
            if(selectedFragment != null){
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, selectedFragment)
                    .commit()
            }
            true

        }
        bottomNavigationView.selectedItemId = R.id.navigation_dashboard_aplc
    }
}