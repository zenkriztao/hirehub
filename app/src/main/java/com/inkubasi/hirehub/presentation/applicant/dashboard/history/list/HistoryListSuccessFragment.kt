package com.inkubasi.hirehub.presentation.applicant.dashboard.history.list

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.inkubasi.hirehub.R
import com.inkubasi.hirehub.coreapp.utils.Utils
import com.inkubasi.hirehub.databinding.FragmentHistoryListBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HistoryListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HistoryListSuccessFragment : Fragment() {

    companion object{
        private const val ACTION = "arg_action"
        fun newInstance(action: String): HistoryListSuccessFragment{
            val fragment = HistoryListSuccessFragment()
            val args = Bundle()
            args.putString(ACTION, action)
            fragment.arguments = args
            return fragment
        }
    }
//    private var param1: String? = null
    private lateinit var action: String
    private lateinit var viewModel: HistoryApplicantViewModel
    private lateinit var adapter: HistoryAdapter
    private lateinit var dialog: Dialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)



        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        action = arguments?.getString(ACTION) ?: ""
        val binding = FragmentHistoryListBinding.inflate(inflater, container, false)
        val recyclerView = binding.listHistoryApplicant
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = HistoryAdapter(onClick = {

        })
        recyclerView.adapter = adapter
        viewModel = ViewModelProvider(this).get(HistoryApplicantViewModel::class.java)
        dialog = Dialog(requireContext())
        bindViewModels()
        return binding.root
    }

    private fun bindViewModels(){
        viewModel.state.observe(viewLifecycleOwner){
            if(it.isLoading){
                Utils.showLoading(dialog,"Memuat Riwayat")
            }
            if(it.history != null){
                if(it.history.isNotEmpty()){
                    Utils.hideLoading(dialog)
                    adapter.updateData(it.history)
                } else {
                    Utils.hideLoading(dialog)
                }
            }
            if(it.error.isNotEmpty()){
                Utils.hideLoading(dialog)
            }
        }
    }

}