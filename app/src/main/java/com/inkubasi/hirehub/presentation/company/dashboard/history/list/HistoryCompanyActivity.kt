package com.inkubasi.hirehub.presentation.company.dashboard.history.list

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.inkubasi.hirehub.R
import com.inkubasi.hirehub.coreapp.data.source.remote.newresponse.GetOfferHistoryCompany
import com.inkubasi.hirehub.coreapp.domain.model.User
import com.inkubasi.hirehub.coreapp.utils.Utils
import com.inkubasi.hirehub.coreapp.utils.constant.HistoryType
import com.inkubasi.hirehub.databinding.ActivityHistoryCompanyBinding
import com.inkubasi.hirehub.presentation.company.dashboard.history.detail.HistoryDetailActivity
import com.inkubasi.hirehub.presentation.company.dashboard.history.detail.HistoryDetailActivity.Companion.EXTRA_ID_APPLICANT
import com.inkubasi.hirehub.presentation.company.dashboard.history.detail.HistoryDetailActivity.Companion.EXTRA_ID_OFFER
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryCompanyActivity : AppCompatActivity() {
    private var actionType: String? = null
    private lateinit var binding: ActivityHistoryCompanyBinding
    private val viewModel: HistoryCompanyViewModel by viewModel()
    private lateinit var user: User
    private var token: String? = null
    private lateinit var dialog: Dialog
    private lateinit var adapter: HistoryCompanyAdapter
    private var history: GetOfferHistoryCompany.Offer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityHistoryCompanyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dialog = Dialog(this)
        actionType = intent.getStringExtra(ACTION_HISTORY)

        if (actionType != null) {
            setupUserAndHistory(actionType)
            setupRecyclerView()
            if (actionType == HistoryType.SUCCESS) {
                binding.title.text = getString(R.string.success)
                binding.img.setImageResource(R.drawable.ic_empty_success)
                binding.caption.text = getString(R.string.ayo_terus_mencari_kandidat_yang_cocok_dengan_perusahaanmu_yaa)
            } else if (actionType == HistoryType.REJECT) {
                binding.title.text = getString(R.string.reject)
                binding.caption.text = getString(R.string.belum_ada_penolakan)
                binding.img.setImageResource(R.drawable.ic_empty_reject)
            } else if (actionType == HistoryType.ON_PROCESS) {
                binding.title.text = getString(R.string.on_process)
                binding.img.setImageResource(R.drawable.ic_empty_waiting)
                binding.caption.text = getString(R.string.belum_ada_waiting_company)
            }
            bindViewModels()
        }
    }

    private fun setupUserAndHistory(actionType: String?) {
        viewModel.getUser().observe(this) { userResult ->
            userResult?.let {
                user = it
                token = user.token
                binding.title.text = getTitleForActionType(actionType.orEmpty())
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getOfferHistoryByAction(actionType.orEmpty(), user.token)
    }

    private fun setupRecyclerView() {
        val recyclerView = binding.listHistoryCompany
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = HistoryCompanyAdapter(history = {
            when (it.status) {
                "pending" -> {
                    val intent = Intent(this, HistoryDetailActivity::class.java)
                    intent.putExtra(EXTRA_ID_APPLICANT, it.applicantId)
                    intent.putExtra(EXTRA_ID_OFFER, it.id)
                    startActivity(intent)
                }
                else -> {}
            }
        })
        recyclerView.adapter = adapter
    }

    private fun bindViewModels() {
        viewModel.state.observe(this) { state ->
            when {
                state.isLoading -> {
                    Utils.showLoading(dialog, "Loading History")
                }
                state.history != null -> {
                    if(state.history.isNotEmpty()){
                        Utils.hideLoading(dialog)
                        adapter.updateData(state.history)

                    }
                    else {
                        Utils.hideLoading(dialog)
                        binding.listHistoryCompany.visibility = View.GONE
                        binding.emptylistsuccess.visibility = View.VISIBLE

                    }

                }
                state.error.isNotEmpty() -> {
                    Utils.hideLoading(dialog)
                    history = null
                }
            }
        }
    }

    private fun getTitleForActionType(actionType: String): String {
        return when (actionType) {
            HistoryType.SUCCESS -> getString(R.string.success)
            HistoryType.REJECT -> getString(R.string.reject)
            HistoryType.ON_PROCESS -> getString(R.string.on_process)
            else -> ""
        }
    }

    companion object {
        const val ACTION_HISTORY = "ACTION_HISTORY"
    }
}
