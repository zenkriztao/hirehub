package com.inkubasi.hirehub.presentation.company.dashboard.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.inkubasi.hirehub.R
import com.inkubasi.hirehub.coreapp.data.source.remote.newresponse.ApplicantListResponse
import com.inkubasi.hirehub.coreapp.ui.adapter.ApplicantFilterAdapter
import com.inkubasi.hirehub.coreapp.utils.EndlessRecyclerOnScrollListener
import com.inkubasi.hirehub.coreapp.utils.bottomsheet.FilterRecommendationBottomSheet
import com.inkubasi.hirehub.coreapp.utils.hideKeyboard
import com.inkubasi.hirehub.databinding.FragmentHomeCompanyBinding
import com.inkubasi.hirehub.presentation.company.dashboard.home.offer.CompanyOfferDetailActivity
import com.inkubasi.hirehub.presentation.company.dashboard.home.offer.CompanyOfferDetailActivity.Companion.EXTRA_APPLICANT
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeCompanyFragment : Fragment() {

    private lateinit var binding: FragmentHomeCompanyBinding

    private var infinityScrollListener: EndlessRecyclerOnScrollListener? = null
    private val viewModel: HomeCompanyViewModel by viewModel()
    private lateinit var token: String
    private var search: String? = ""
    private var filterRecommendationBottomSheet: FilterRecommendationBottomSheet? = null
    private var isApplyFilter: Boolean = false

    private val mAdapter by lazy {
        ApplicantFilterAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeCompanyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindViewModels()
        initRecyclerView()
        bindViewEvents()
    }

    private fun bindViewEvents() {
        with(binding) {
            svField.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    isApplyFilter = false
                    svField.setQuery("", false)
                    search = query
                    binding.root.hideKeyboard()
                    viewModel.fetchApplicant(token, query.orEmpty())
                    return true
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    return false
                }
            })
            filterRecommendation.setOnClickListener {
                showProfileAddBottomSheet()
            }
        }
    }

    private fun showProfileAddBottomSheet() {
        filterRecommendationBottomSheet = null
        filterRecommendationBottomSheet = FilterRecommendationBottomSheet.newInstance(
            onClickData = {
                isApplyFilter = true
                viewModel.fetchFilter(token, it)
            }
        )
        filterRecommendationBottomSheet?.show(
            parentFragmentManager,
            FilterRecommendationBottomSheet.TAG
        )
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchApplicant(token, search.orEmpty())
    }

    private fun initRecyclerView() {
        with(binding) {
            val mLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            rvList.apply {
                layoutManager = mLayoutManager
                adapter = mAdapter
                mAdapter.onItemClick = {
                    val intent = Intent(requireContext(), CompanyOfferDetailActivity::class.java)
                    intent.putExtra(EXTRA_APPLICANT, it)
                    startActivity(intent)
                }
            }
            infinityScrollListener = object : EndlessRecyclerOnScrollListener(mLayoutManager) {
                override fun onLoadMore() {
                    if (!isApplyFilter) (
                            viewModel.fetchApplicant(token, search.orEmpty())
                            )
                }
            }
            rvList.addOnScrollListener(infinityScrollListener!!)
            srContent.setColorSchemeResources(
                R.color.ds_accent_blue,
                R.color.ds_accent_purple,
                R.color.ds_accent_pink
            )
            srContent.setOnRefreshListener {
                infinityScrollListener?.resetEndless()
                viewModel.refreshData()
                search = ""
                viewModel.fetchApplicant(token, search.orEmpty())
            }
        }
    }

    private fun bindViewModels() {
        viewModel.getUser().observe(this) {
            token = it.token
        }
        viewModel.contentData.observe(this) {
            isApplyFilter = false
            mAdapter.setData(it)
        }
        viewModel.loadingState.observe(this) {
            infinityScrollListener?.isLoading = it
            with(binding) {
                if (srContent.isRefreshing != it) {
                    srContent.isRefreshing = it
                }
            }
        }
        viewModel.contentFilter.observe(this) {
            isApplyFilter = true
            val data = it.map { applicant ->
                ApplicantListResponse.Applicant(
                    id = applicant.id,
                    username = applicant.username,
                    name = applicant.name,
                    summary = applicant.summary,
                    field = applicant.field,
                    salaryMin = applicant.salaryMin,
                    imageUrl = applicant.imageUrl
                )
            }
            mAdapter.setData(data)
            mAdapter.notifyDataSetChanged()
        }
    }
}

