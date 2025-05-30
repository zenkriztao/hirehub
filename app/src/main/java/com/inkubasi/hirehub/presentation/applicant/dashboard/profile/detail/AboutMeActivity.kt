package com.inkubasi.hirehub.presentation.applicant.dashboard.profile.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.inkubasi.hirehub.R
import com.inkubasi.hirehub.coreapp.data.source.remote.newresponse.GetProfileApplicantResponse
import com.inkubasi.hirehub.databinding.ActivityAboutMeBinding
import com.inkubasi.hirehub.presentation.applicant.dashboard.profile.detail.aboutme.SectionPagerAdapter
import com.inkubasi.hirehub.presentation.applicant.dashboard.profile.menu.edit.EditProfileActivity

class AboutMeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAboutMeBinding
    private var profile : GetProfileApplicantResponse? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutMeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        profile = intent.getParcelableExtra(EXTRA_ABOUT)

        if (profile != null){
            binding.viewProfile.visibility = View.VISIBLE
            setProfileAbout()
        }else{
            binding.viewProfileEmpty.visibility = View.VISIBLE

        }

        binding.btnActionEmpty.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
        }

        binding.btnSeeCv.setOnClickListener {
            if(profile?.data?.applicant?.pdfUrl != null){
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("http://docs.google.com/gview?embedded=true&url=" + profile?.data?.applicant?.pdfUrl)
                )
                startActivity(intent)
            }
        }

        val sectionPagerAdapter = SectionPagerAdapter(this)
        binding.viewPager.adapter = sectionPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager){
                tabs, position ->
            tabs.text = resources.getString(TAB_TITLES[position])
        }.attach()

    }

    private fun setProfileAbout() {
        binding.tvNameAbout.text = profile?.data?.applicant?.name
        binding.tvFieldTitle.text =  profile?.data?.applicant?.field
        Glide.with(this@AboutMeActivity)
            .load(profile?.data?.applicant?.imageUrl)
            .into(binding.ivProfileApplicant)
    }


    companion object {
        const val EXTRA_ABOUT = "extra_about"
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}