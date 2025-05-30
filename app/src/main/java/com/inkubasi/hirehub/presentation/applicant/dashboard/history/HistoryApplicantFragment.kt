package com.inkubasi.hirehub.presentation.applicant.dashboard.history

import android.content.Intent
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.inkubasi.hirehub.R
import com.inkubasi.hirehub.coreapp.domain.model.User
import com.inkubasi.hirehub.coreapp.utils.constant.HistoryType
import com.inkubasi.hirehub.databinding.FragmentHistoryApplicantBinding

import com.inkubasi.hirehub.presentation.applicant.dashboard.history.list.HistoryApplicantActivity
import com.inkubasi.hirehub.presentation.applicant.dashboard.history.list.HistoryApplicantActivity.Companion.ACTION_HISTORY
import com.inkubasi.hirehub.presentation.applicant.dashboard.history.list.HistoryApplicantViewModel
import com.inkubasi.hirehub.presentation.applicant.dashboard.history.list.HistoryListFragment

class HistoryApplicantFragment : Fragment() {
    private lateinit var user: User
    private var token: String? = null

    private lateinit var binding: FragmentHistoryApplicantBinding
    private lateinit var viewPager: ViewPager
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryApplicantBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager = binding.viewPager

        val adapter = ViewPagerAdapter(childFragmentManager)
        adapter.addFragment(HistoryListFragment.newInstance(HistoryType.ON_PROCESS), "On Process")
        adapter.addFragment(HistoryListFragment.newInstance(HistoryType.SUCCESS), "Success")
        adapter.addFragment(HistoryListFragment.newInstance(HistoryType.REJECT), "Reject")

        viewPager.adapter = adapter
        binding.tabLayout.setupWithViewPager(viewPager)



//        binding.buttonOnProcess.setOnClickListener{
//            val action = HistoryType.ON_PROCESS
//            val intent = Intent(activity, HistoryApplicantActivity::class.java)
//            intent.putExtra(ACTION_HISTORY, action)
//            startActivity(intent)
//        }

//        binding.buttonSuccess.setOnClickListener{
//            val action = HistoryType.SUCCESS
//            val intent = Intent(activity, HistoryApplicantActivity::class.java)
//            intent.putExtra(ACTION_HISTORY, action)
//            startActivity(intent)
//        }

//        binding.buttonReject.setOnClickListener{
//            val action = HistoryType.REJECT
//            val intent = Intent(activity, HistoryApplicantActivity::class.java)
//            intent.putExtra(ACTION_HISTORY, action)
//            startActivity(intent)
//        }

    }
    inner class ViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){
        private val fragmentList = ArrayList<Fragment>()
        private val titleList = ArrayList<String>()

        override fun getItem(position: Int): Fragment{
            return fragmentList[position]
        }

        override fun getCount(): Int {
            return fragmentList.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titleList[position]
        }

        fun addFragment(fragment: Fragment, title: String){
            fragmentList.add(fragment)
            titleList.add(title)
        }

    }

}