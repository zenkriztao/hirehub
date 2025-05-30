package com.inkubasi.hirehub.presentation.applicant.dashboard.profile.detail.aboutme

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.chip.Chip
import com.inkubasi.hirehub.R
import com.inkubasi.hirehub.coreapp.data.source.remote.newresponse.GetProfileApplicantResponse
import com.inkubasi.hirehub.coreapp.utils.Utils
import com.inkubasi.hirehub.databinding.FragmentAboutMeBinding
import com.inkubasi.hirehub.presentation.applicant.dashboard.profile.detail.AboutMeActivity

class AboutMeFragment : Fragment() {

    private var profile : GetProfileApplicantResponse? = null
    private lateinit var binding: FragmentAboutMeBinding
    private val chipTextList = mutableListOf<String>()
    private val chipLanguageList = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAboutMeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        profile = requireActivity().intent.getParcelableExtra(AboutMeActivity.EXTRA_ABOUT)
        if (profile != null){
            setProfileAbout()
        }
    }

    private fun setProfileAbout() {
        binding.tvDescAbout.text =  profile?.data?.applicant?.summary
        binding.tvEducationAbout.text = profile?.data?.applicant?.degree
        binding.tvLocationAbout.text =profile?.data?.applicant?.location
        binding.tvDateAbout.text = profile?.data?.applicant?.dateOfBirth?.let { Utils.formatDate(it) }
        binding.tvInstitusiAbout.text = profile?.data?.applicant?.institution
        profile?.data?.applicant?.skills?.forEach { skill ->
            Chip(requireContext()).apply {
                chipTextList.add(skill)
                text = skill
                setChipBackgroundColorResource(R.color.bg_chip)
                binding.chipGroup.addView(this)
            }
        }
        profile?.data?.applicant?.language?.forEach { language ->
            Chip(requireContext()).apply {
                chipLanguageList.add(language)
                text = language
                setChipBackgroundColorResource(R.color.bg_chip)
                binding.chipGroup2.addView(this)
            }
        }

    }

}