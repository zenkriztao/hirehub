package com.inkubasi.hirehub.presentation.applicant.dashboard.home

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.inkubasi.hirehub.coreapp.utils.Utils
import com.inkubasi.hirehub.databinding.FragmentDashboardApplicantBinding
import com.inkubasi.hirehub.presentation.applicant.dashboard.history.list.HistoryAdapter
import com.inkubasi.hirehub.presentation.applicant.dashboard.home.detail.DetailCompanyActivity
import com.inkubasi.hirehub.presentation.applicant.dashboard.home.detail.DetailCompanyActivity.Companion.EXTRA_COMPANY
import com.inkubasi.hirehub.presentation.applicant.dashboard.home.detail.DetailCompanyActivity.Companion.EXTRA_ID_OFFER
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeApplicantFragment : Fragment() {

    private lateinit var binding: FragmentDashboardApplicantBinding
    private lateinit var mAdapter: HistoryAdapter
    private val viewModel: HomeApplicantViewModel by viewModel()
    private lateinit var dialog: Dialog
    private lateinit var token: String


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentDashboardApplicantBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        bindViewModel()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getOfferHistoryByAction(token)
    }

    private fun initView() {
        dialog = Dialog(requireContext())
        viewModel.getUser().observe(this) {
            token = it.token
        }
        with(binding) {
            val mLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            rvList.apply {
                layoutManager = mLayoutManager
                mAdapter = HistoryAdapter(onClick = {
                    val intent = Intent(requireContext(), DetailCompanyActivity::class.java)
                    intent.putExtra(EXTRA_COMPANY, it.companyId)
                    intent.putExtra(EXTRA_ID_OFFER, it.id)
                    startActivity(intent)
                })
                adapter = mAdapter
            }
        }
    }

    private fun bindViewModel() {
        viewModel.loadingState.observe(this) {
            if (it) Utils.showLoading(dialog, "Loading")
            else Utils.hideLoading(dialog)
        }
        viewModel.stateCompanyList.observe(this) {
            with(binding) {
                if (it.isEmpty()) {
                    linearEmpty.visibility = View.VISIBLE
                    rvList.visibility = View.GONE
                } else {
                    mAdapter.updateData(it)
                }
            }
        }
    }
}