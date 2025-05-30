package com.inkubasi.hirehub.presentation.applicant.dashboard.home.detail

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.inkubasi.hirehub.R
import com.inkubasi.hirehub.coreapp.data.source.remote.newresponse.GetProfileCompanyResponse
import com.inkubasi.hirehub.coreapp.utils.Utils
import com.inkubasi.hirehub.coreapp.utils.bottomsheet.ConfirmationBottomSheet
import com.inkubasi.hirehub.coreapp.utils.bottomsheet.InfoBottomSheet
import com.inkubasi.hirehub.databinding.ActivityDetailCompanyBinding
import com.inkubasi.hirehub.presentation.chat.ChannelActivity
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.models.User
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailCompanyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailCompanyBinding
    private val viewModel: DetailCompanyViewModel by viewModel()
    private lateinit var dialog: Dialog
    private lateinit var token: String
    private lateinit var user: User
    private lateinit var companyId: String
    private lateinit var userIdApplicant: String
    private lateinit var userIdCompany: String
    private val client: ChatClient by inject()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailCompanyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        initView()
        bindViewModels()
        bindViewEvent()
    }

    private fun initView() {
        dialog = Dialog(this)
        val idCompany = intent.getIntExtra(EXTRA_COMPANY, 0)
        val idOffer = intent.getIntExtra(EXTRA_ID_OFFER, 0)
        viewModel.getUser().observe(this) {
            token = it.token
            userIdApplicant = it.id
            viewModel.getDetailCompanyById(it.token, idCompany.toString())
            if (client.getCurrentUser() == null) {
                user = User(
                    it.id,
                    extraData = mutableMapOf(
                        "name" to it.name
                    )
                )
            }
            client.connectUser(
                user,
                it.tokenStream
            ).enqueue()
        }
        companyId = idOffer.toString()
    }

    private fun bindViewModels() {
        viewModel.stateProfile.observe(this) {
            setCompanyProfile(it.data?.company)
        }
        viewModel.stateLoading.observe(this) {
            if (it) Utils.showLoading(dialog, "Loading")
            else Utils.hideLoading(dialog)
        }
        viewModel.stateError.observe(this) {
            Toast.makeText(this, getString(R.string.failed_accept_offer), Toast.LENGTH_SHORT).show()
            finish()
        }
        viewModel.stateApplicantAccept.observe(this) {
            client.createChannel(
                channelType = "messaging",
                channelId = "",
                memberIds = listOf(userIdApplicant, it),
                extraData = emptyMap()
            ).enqueue { result ->
                if (result.isSuccess) {
                    val data = result.getOrNull()
                    Log.d("Result success", data.toString())
                    InfoBottomSheet.newInstance(
                        onAccept = {
                            navigateToChatPage(data?.cid.orEmpty())
                        },
                        title = getString(R.string.success_create_offer),
                        description = getString(R.string.success_create_offer_desc),
                        titleButton = getString(R.string.understand)
                    ).show(supportFragmentManager, InfoBottomSheet.TAG)
                    Log.d("createChannel result success", "$result")
                } else {
                    Log.d("createChannel result error", "$result")
                }
            }

        }
        viewModel.stateApplicantReject.observe(this) {
            Toast.makeText(this, getString(R.string.succed_reject_offer), Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun setCompanyProfile(it: GetProfileCompanyResponse.CompanyInfo?) {
        with(binding) {
            userIdCompany = it?.id.toString()
            tvCompany.text = it?.name
            headOfficeCompanyDesc.text = it?.office
            tvLocation.text = it?.location
            websiteCompanyDesc.text = it?.webUrl
            employeeCompanyDesc.text = it?.employee
            aboutCompanyDesc.text = it?.description
            Glide.with(this@DetailCompanyActivity)
                .load(it?.imageUrl ?: R.drawable.companyava)
                .into(ivProfileCompany)
        }
    }

    private fun navigateToChatPage(cid: String) {
        val intent = Intent(this, ChannelActivity::class.java)
        intent.putExtra(ChannelActivity.CHANNEL_ID, cid)
        startActivity(intent)
        finish()
    }


    private fun bindViewEvent() {
        with(binding) {
            btnAccept.setOnClickListener {
                showOfferAcceptBottomSheet()
            }
            btnReject.setOnClickListener {
                showOfferRejectBottomSheet()
            }
        }
    }

    private fun showOfferAcceptBottomSheet() {
        ConfirmationBottomSheet.newInstance(
            onAccept = {
                viewModel.applicantAcceptOffer(token, companyId)
            },
            onReject = {},
            title = getString(R.string.accept_offer),
            description = getString(R.string.accept_offer_desc),
            titleButton = getString(R.string.accept_offer)
        ).show(supportFragmentManager, ConfirmationBottomSheet.TAG)
    }

    private fun showOfferRejectBottomSheet() {
        ConfirmationBottomSheet.newInstance(
            onAccept = {
                viewModel.applicantRejectOffer(token, companyId)
            },
            onReject = {},
            title = getString(R.string.reject_offer),
            description = getString(R.string.reject_offer_desc),
            titleButton = getString(R.string.reject_offer)
        ).show(supportFragmentManager, ConfirmationBottomSheet.TAG)
    }

    companion object {
        const val EXTRA_COMPANY = "EXTRA_COMPANY"
        const val EXTRA_ID_OFFER = "EXTRA_ID_OFFER"
    }
}