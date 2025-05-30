package com.inkubasi.hirehub.presentation.applicant.dashboard.history.list

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.inkubasi.hirehub.R
import com.inkubasi.hirehub.coreapp.data.source.remote.newresponse.GetOfferHistoryApplicant
import com.inkubasi.hirehub.coreapp.domain.model.User
import com.inkubasi.hirehub.coreapp.utils.Utils
import com.inkubasi.hirehub.coreapp.utils.constant.HistoryType
import com.inkubasi.hirehub.databinding.ActivityHistoryApplicantBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryApplicantActivity : AppCompatActivity() {
    private val viewModel: HistoryApplicantViewModel by viewModel()
    private lateinit var user: User
    private var token: String? = null
    private lateinit var dialog : Dialog
    private lateinit var binding : ActivityHistoryApplicantBinding
    private lateinit var adapter: HistoryAdapter
    private var history: GetOfferHistoryApplicant.Offer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityHistoryApplicantBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dialog = Dialog(this)
        val actionType = intent.getStringExtra(ACTION_HISTORY)

        if (actionType != null){
            viewModel.getUser().observe(this){
                user = it
                token = it.token
                viewModel.getOfferHistoryByAction(actionType, user.token)

                if (actionType == HistoryType.SUCCESS) {
                    binding.title.text = getString(R.string.success)
                    binding.img.setImageResource(R.drawable.ic_empty_success)
                    binding.caption.text = getString(R.string.ayo_terus_mencari_dan_temukan_perusahaan_yang_cocok_denganmu_yaa_semoga_berhasill)
                } else if (actionType == HistoryType.REJECT) {
                    binding.title.text = getString(R.string.reject)
                    binding.caption.text = getString(R.string.belum_ada_penolakan)
                    binding.img.setImageResource(R.drawable.ic_empty_reject)
                } else if (actionType == HistoryType.ON_PROCESS) {
                    binding.title.text = getString(R.string.on_process)
                    binding.img.setImageResource(R.drawable.ic_empty_waiting)
                    binding.caption.text = getString(R.string.belum_ada_waiting)
                }
            }
            val recyclerView = binding.listHistoryApplicant
            recyclerView.layoutManager = LinearLayoutManager(this)
            adapter = HistoryAdapter(onClick = {

            })
            recyclerView.adapter = adapter
            bindViewModels()

        }

    }


    private fun bindViewModels(){
        viewModel.state.observe(this){
            if(it.isLoading){
                Utils.showLoading(dialog, "Loading History")
            }
            if(it.history != null){
                if(it.history.isNotEmpty()){
                    Utils.hideLoading(dialog)
                    adapter.updateData(it.history)
                }
                else {
                    Utils.hideLoading(dialog)
                    binding.listHistoryApplicant.visibility = View.GONE
                    binding.emptylistsuccess.visibility = View.VISIBLE
                }

            }

            if(it.error.isNotEmpty()){
                Utils.hideLoading(dialog)
                 history = null
            }

        }
    }
    companion object {
        const val ACTION_HISTORY = "ACTION_HISTORY"

    }
}

