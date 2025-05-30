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
import com.inkubasi.hirehub.coreapp.databinding.BottomSheetSignOutConfirmationBinding

class ConfirmationBottomSheet(
    private val onAccept: () -> Unit,
    private val onReject: () -> Unit,
    private val title: String,
    private val description: String,
    private val titleButton: String
) : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetSignOutConfirmationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetSignOutConfirmationBinding.inflate(inflater, container, false)
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
            btnSignOut.text = titleButton
        }
    }

    private fun bindViewEvents() {
        with(binding) {
            btnCancel.setOnClickListener {
                onReject.invoke()
                dismiss()
            }
            btnSignOut.setOnClickListener {
                onAccept.invoke()
                dismiss()
            }
        }
    }

    companion object {
        val TAG
            get() = "TAG.${BuildConfig.LIBRARY_PACKAGE_NAME}.${ConfirmationBottomSheet::class.java.simpleName}"

        fun newInstance(
            onAccept: () -> Unit,
            onReject: () -> Unit,
            title: String,
            description: String,
            titleButton: String
        ) = ConfirmationBottomSheet(onAccept, onReject, title, description, titleButton)
    }
}