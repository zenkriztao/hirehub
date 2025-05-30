package com.inkubasi.hirehub.coreapp.utils.bottomsheet

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.inkubasi.hirehub.coreapp.BuildConfig
import com.inkubasi.hirehub.coreapp.databinding.UploadPhotoBottomSheetBinding

class UploadPhotoBottomSheet(
    private val onClickGallery: () -> Unit,
    private val imageUrl : String
) : BottomSheetDialogFragment()  {

    private lateinit var binding : UploadPhotoBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = UploadPhotoBottomSheetBinding.inflate(inflater, container, false)
        return  binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        bindViewEvents()
    }

    private fun initView() {
        with(binding){
           if (imageUrl.isNotBlank()){
               Glide.with(requireContext())
                   .load(imageUrl)
                   .into(imageProfile)
           }
        }
    }


    private fun bindViewEvents() {
        with(binding) {
            btnActionGallery.setOnClickListener {
                onClickGallery.invoke()
                dismiss()
            }
            btnClose.setOnClickListener {
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
            get() = "TAG.${BuildConfig.LIBRARY_PACKAGE_NAME}.${UploadPhotoBottomSheet::class.java.simpleName}"

        fun newInstance(
            onClickGallery: () -> Unit,
            imageUrl : String
        ) = UploadPhotoBottomSheet(
            onClickGallery,
            imageUrl
        )
    }
}