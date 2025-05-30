package com.inkubasi.hirehub.presentation.applicant.dashboard.profile.detail.aboutme

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.inkubasi.hirehub.coreapp.data.source.remote.newresponse.GetProfileApplicantResponse
import com.inkubasi.hirehub.databinding.FragmentContactBinding
import com.inkubasi.hirehub.presentation.applicant.dashboard.profile.detail.AboutMeActivity

class ContactFragment : Fragment() {

    private var profile : GetProfileApplicantResponse? = null
    private lateinit var binding : FragmentContactBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentContactBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profile = requireActivity().intent.getParcelableExtra(AboutMeActivity.EXTRA_ABOUT)
        if (profile != null){
            setProfileContact()
        }

    }

    private fun setProfileContact() {
        binding.tvEmailAbout.text = profile?.data?.applicant?.email
        binding.tvPhoneAbout.text  =  profile?.data?.applicant?.phone
    }
}