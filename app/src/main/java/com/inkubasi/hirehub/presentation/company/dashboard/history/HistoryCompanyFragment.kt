package com.inkubasi.hirehub.presentation.company.dashboard.history

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.inkubasi.hirehub.coreapp.utils.constant.HistoryType
import com.inkubasi.hirehub.databinding.FragmentHistoryCompanyBinding
import com.inkubasi.hirehub.presentation.company.dashboard.history.list.HistoryCompanyActivity

class HistoryCompanyFragment : Fragment() {
    private lateinit var binding: FragmentHistoryCompanyBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryCompanyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonOnProcess.setOnClickListener {
            startHistoryActivity(HistoryType.ON_PROCESS)
        }

        binding.buttonSuccess.setOnClickListener {
            startHistoryActivity(HistoryType.SUCCESS)
        }

        binding.buttonReject.setOnClickListener {
            startHistoryActivity(HistoryType.REJECT)
        }
    }

    private fun startHistoryActivity(action: String) {
        val intent = Intent(activity, HistoryCompanyActivity::class.java)
        intent.putExtra(HistoryCompanyActivity.ACTION_HISTORY, action)
        startActivity(intent)
    }
}
