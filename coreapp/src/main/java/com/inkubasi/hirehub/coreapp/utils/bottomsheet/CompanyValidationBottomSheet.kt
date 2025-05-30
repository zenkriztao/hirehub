package com.inkubasi.hirehub.coreapp.utils.bottomsheet

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.inkubasi.hirehub.coreapp.BuildConfig
import com.inkubasi.hirehub.coreapp.databinding.CompanyValidationBottomSheetBinding

class CompanyValidationBottomSheet(private val onClickData: ()-> Unit, ) : BottomSheetDialogFragment() {

    private lateinit var binding: CompanyValidationBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CompanyValidationBottomSheetBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewEvents()
    }

    private fun bindViewEvents(){
        with(binding){
            btnContact.setOnClickListener{
               onClickData.invoke()
                dismiss()
            }
        }
    }

    companion object{
        val TAG
            get() = "TAG.${BuildConfig.LIBRARY_PACKAGE_NAME}.${CompanyValidationBottomSheet::class.java.simpleName}"

        fun newInstance(onClickData: () -> Unit) =
            CompanyValidationBottomSheet(onClickData)
    }
}