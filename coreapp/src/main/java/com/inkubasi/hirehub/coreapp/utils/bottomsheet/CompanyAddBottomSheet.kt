package com.inkubasi.hirehub.coreapp.utils.bottomsheet

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.inkubasi.hirehub.coreapp.BuildConfig
import com.inkubasi.hirehub.coreapp.databinding.CompanyAddBottomSheetBinding

class CompanyAddBottomSheet(
    private val onClickData: () -> Unit,
) : BottomSheetDialogFragment()  {

    private lateinit var binding : CompanyAddBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CompanyAddBottomSheetBinding.inflate(inflater, container, false)
        return  binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewEvents()
    }


    private fun bindViewEvents() {
        with(binding) {
            btnClose.setOnClickListener {
                dismiss()
            }
            btnActionData.setOnClickListener {
                onClickData.invoke()
                dismiss()
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        (super.onCreateDialog(savedInstanceState) as BottomSheetDialog).apply {
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            setCancelable(false)
            setCanceledOnTouchOutside(false)
        }

    companion object {
        val TAG
            get() = "TAG.${BuildConfig.LIBRARY_PACKAGE_NAME}.${CompanyAddBottomSheet::class.java.simpleName}"

        fun newInstance(
            onClickData: () -> Unit
        ) = CompanyAddBottomSheet(
            onClickData
        )
    }
}