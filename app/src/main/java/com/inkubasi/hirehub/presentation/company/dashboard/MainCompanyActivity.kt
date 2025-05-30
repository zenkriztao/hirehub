package com.inkubasi.hirehub.presentation.company.dashboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.inkubasi.hirehub.R
import com.inkubasi.hirehub.databinding.ActivityMainCompanyBinding
import com.inkubasi.hirehub.presentation.company.dashboard.chat.ChatCompanyFragment
import com.inkubasi.hirehub.presentation.company.dashboard.history.HistoryCompanyFragment
import com.inkubasi.hirehub.presentation.company.dashboard.home.HomeCompanyFragment
import com.inkubasi.hirehub.presentation.company.dashboard.profile.ProfileCompanyFragment

class MainCompanyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainCompanyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainCompanyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.bottomNavCompany.setOnNavigationItemSelectedListener {
                item ->
            var selectedFragment: Fragment? = null

            when (item.itemId){
                R.id.navigation_home_company -> selectedFragment = HomeCompanyFragment()
                R.id.navigation_chat_company -> selectedFragment = ChatCompanyFragment()
                R.id.navigation_history_company-> selectedFragment = HistoryCompanyFragment()
                R.id.navigation_profile_company -> selectedFragment = ProfileCompanyFragment()
            }
            if(selectedFragment != null){
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container_company, selectedFragment)
                    .commit()
            }
            true

        }
        binding.bottomNavCompany.selectedItemId = R.id.navigation_home_company
    }
}