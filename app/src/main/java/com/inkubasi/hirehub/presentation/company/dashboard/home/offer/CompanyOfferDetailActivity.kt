package com.inkubasi.hirehub.presentation.company.dashboard.home.offer

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.inkubasi.hirehub.R
import com.inkubasi.hirehub.coreapp.data.source.remote.newresponse.ApplicantListResponse
import com.inkubasi.hirehub.coreapp.data.source.remote.newresponse.GetProfileApplicantResponse
import com.inkubasi.hirehub.coreapp.utils.Utils
import com.inkubasi.hirehub.coreapp.utils.bottomsheet.ConfirmationBottomSheet
import com.inkubasi.hirehub.coreapp.utils.bottomsheet.SignOutConfirmationBottomSheet
import com.inkubasi.hirehub.databinding.ActivityCompanyOfferDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class CompanyOfferDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCompanyOfferDetailBinding
    private val viewModel: CompanyOfferDetailViewModel by viewModel()
    private lateinit var dialog: Dialog
    private val chipTextList = mutableListOf<String>()
    private lateinit var token: String
    private lateinit var applicantId: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCompanyOfferDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        dialog = Dialog(this)

        initView()
        bindViewEvent()
        bindViewModels()
    }

    private fun bindViewModels() {
        viewModel.stateLoading.observe(this) {
            if (it) Utils.showLoading(dialog, "Loading")
            else Utils.hideLoading(dialog)
        }
        viewModel.stateSuccess.observe(this) { profile ->
            profile?.let {
                setProfile(it.data?.applicant)
            }
        }
        viewModel.stateProcessSuccess.observe(this) {
            Toast.makeText(this, getString(R.string.succed_create_offer), Toast.LENGTH_SHORT).show()
            finish()
        }
        viewModel.stateProcessError.observe(this) {
            Toast.makeText(this, getString(R.string.failed_create_offer), Toast.LENGTH_SHORT).show()
        }
    }

    private fun setProfile(it: GetProfileApplicantResponse.ApplicantData?) {
        with(binding) {
            applicantId = it?.id.toString()
            tvName.text = it?.name ?: getString(R.string.no_data_applicant)
            tvRole.text = it?.field ?: getString(R.string.no_data_applicant)
            Glide.with(this@CompanyOfferDetailActivity)
                .load(it?.imageUrl ?: R.drawable.ic_profile_prev)
                .into(ivProfileApplicant)
            tvDescAbout.text = it?.summary
            tvEducationAbout.text = it?.degree ?: getString(R.string.no_data_applicant)
            tvLocationAbout.text = it?.location ?: getString(R.string.no_data_applicant)
            tvDateAbout.text = it?.dateOfBirth?.let { Utils.formatDate(it) }
                ?: getString(R.string.no_data_applicant)
            tvInstitusiAbout.text = it?.institution ?: getString(R.string.no_data_applicant)
            it?.skills?.forEach { skill ->
                Chip(this@CompanyOfferDetailActivity).apply {
                    chipTextList.add(skill)
                    text = skill
                    setChipBackgroundColorResource(R.color.bg_chip)
                    binding.chipGroup.addView(this)
                }
            }
            it?.language?.forEach { language ->
                Chip(this@CompanyOfferDetailActivity).apply {
                    chipTextList.add(language)
                    text = language
                    setChipBackgroundColorResource(R.color.bg_chip)
                    binding.chipGroup.addView(this)
                }
            }
            tvEmailAbout.text = it?.email ?: getString(R.string.no_data_applicant)
            tvPhoneAbout.text = it?.phone ?: getString(R.string.no_data_applicant)
            buttonCv.setOnClickListener { view ->
                if (it?.pdfUrl != null) {
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("http://docs.google.com/gview?embedded=true&url=" + it.pdfUrl)
                    )
                    startActivity(intent)
                } else {
                    Toast.makeText(
                        this@CompanyOfferDetailActivity,
                        getString(R.string.no_cv_applicant),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun initView() {
        val intent = intent.getParcelableExtra<ApplicantListResponse.Applicant>(EXTRA_APPLICANT)
        intent?.let {
            viewModel.getUser().observe(this) { user ->
                token = user.token
                viewModel.getDetailApplicantById(user.token, it.id.toString())
            }
        }
    }

    private fun bindViewEvent() {
        with(binding) {
            processApplicant.setOnClickListener {
                showOfferBottomSheet()
            }
        }
    }

    private fun showOfferBottomSheet() {
        ConfirmationBottomSheet.newInstance(
            onAccept = {
                viewModel.createOfferApplicant(token, applicantId)
            },
            onReject = {},
            title = getString(R.string.ajukan_penawaran),
            description = getString(R.string.description_create_offer),
            titleButton = getString(R.string.ajukan_penawaran)
        ).show(supportFragmentManager, SignOutConfirmationBottomSheet.TAG)
    }

    companion object {
        const val EXTRA_APPLICANT = "EXTRA_ID_APPLICANT"
    }
}