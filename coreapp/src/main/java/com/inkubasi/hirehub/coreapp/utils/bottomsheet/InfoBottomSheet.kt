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
import com.inkubasi.hirehub.coreapp.databinding.BottomSheetInfoBinding

class InfoBottomSheet(
    private val onAccept: () -> Unit,
    private val title: String,
    private val description: String,
    private val titleButton: String
) : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        (super.onCreateDialog(savedInstanceState) as BottomSheetDialog).apply {
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            setCancelable(false)
            setCanceledOnTouchOutside(false)
        }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        bindViewEvents()
    }

    private fun initView() {
        with(binding) {
            tvTitle.text = title
            tvMessage.text = description
            btnNext.text = titleButton
        }
    }

    private fun bindViewEvents() {
        with(binding) {
            btnNext.setOnClickListener {
                onAccept.invoke()
                dismiss()
            }
        }
    }

    companion object {
        val TAG
            get() = "TAG.${BuildConfig.LIBRARY_PACKAGE_NAME}.${InfoBottomSheet::class.java.simpleName}"

        fun newInstance(
            onAccept: () -> Unit,
            title: String,
            description: String,
            titleButton: String
        ) = InfoBottomSheet(onAccept, title, description, titleButton)
    }
}