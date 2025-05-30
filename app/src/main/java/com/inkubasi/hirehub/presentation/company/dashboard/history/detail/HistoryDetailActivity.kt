package com.inkubasi.hirehub.presentation.company.dashboard.history.detail

import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.inkubasi.hirehub.R
import com.inkubasi.hirehub.coreapp.data.source.remote.newresponse.GetProfileApplicantResponse
import com.inkubasi.hirehub.coreapp.utils.Utils
import com.inkubasi.hirehub.coreapp.utils.bottomsheet.ConfirmationBottomSheet
import com.inkubasi.hirehub.coreapp.utils.bottomsheet.SignOutConfirmationBottomSheet
import com.inkubasi.hirehub.databinding.ActivityHistoryDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryDetailBinding
    private lateinit var dialog: Dialog
    private var applicantId: Int = 0
    private var idOffer: Int = 0
    private lateinit var token: String

    private val viewModel: HistoryDetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        dialog = Dialog(this)
        bindViewModels()
        bindViewEvent()
    }

    private fun bindViewEvent() {
        with(binding) {
            btnHire.setOnClickListener {
                showOfferAccept()
            }
            btnReject.setOnClickListener {
                showOfferReject()
            }
            btnBack.setOnClickListener {
                finish()
            }
        }
    }

    private fun bindViewModels() {
        applicantId = intent.getIntExtra(EXTRA_ID_APPLICANT, 0)
        idOffer = intent.getIntExtra(EXTRA_ID_OFFER, 0)
        viewModel.getUser().observe(this) {
            token = it.token
            viewModel.getDetailApplicantById(it.token, applicantId.toString())
        }
        viewModel.stateLoading.observe(this) {
            if (it) Utils.showLoading(dialog, "Loading")
            else Utils.hideLoading(dialog)
        }
        viewModel.stateSuccess.observe(this) {
            it.data?.applicant.let {
                setProfileApplicant(it)
            }
        }
        viewModel.stateAcceptSuccess.observe(this) {
            Toast.makeText(this, "Success Hire Applicant", Toast.LENGTH_SHORT).show()
            finish()
        }
        viewModel.stateRejectSuccess.observe(this) {
            Toast.makeText(this, "Success Reject Applicant", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun setProfileApplicant(getProfileApplicantResponse: GetProfileApplicantResponse.ApplicantData?) {
        with(binding) {
            summary.text = getProfileApplicantResponse?.summary
            titleName.text = getProfileApplicantResponse?.name
            field.text = getProfileApplicantResponse?.field
            Glide.with(this@HistoryDetailActivity)
                .load(getProfileApplicantResponse?.imageUrl)
                .into(photo)
        }
    }

    private fun showOfferAccept() {
        ConfirmationBottomSheet.newInstance(
            onAccept = {
                viewModel.acceptOfferCompany(token, idOffer.toString())
            },
            onReject = {

            },
            title = getString(R.string.offer_hire),
            description = getString(R.string.offer_hire_desc),
            titleButton = getString(R.string.hire)
        ).show(supportFragmentManager, SignOutConfirmationBottomSheet.TAG)
    }

    private fun showOfferReject() {
        ConfirmationBottomSheet.newInstance(
            onAccept = {
                viewModel.rejectOfferCompany(token, idOffer.toString())
            },
            onReject = {

            },
            title = getString(R.string.offer_reject),
            description = getString(R.string.offer_reject_desc),
            titleButton = getString(R.string.reject)
        ).show(supportFragmentManager, SignOutConfirmationBottomSheet.TAG)
    }

    companion object {
        const val EXTRA_ID_APPLICANT = "EXTRA_ID_APPLICANT"
        const val EXTRA_ID_OFFER = "EXTRA_ID_OFFER"
    }

}