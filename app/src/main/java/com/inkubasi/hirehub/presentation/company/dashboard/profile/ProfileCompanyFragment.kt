package com.inkubasi.hirehub.presentation.company.dashboard.profile

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.inkubasi.hirehub.R
import com.inkubasi.hirehub.coreapp.data.source.remote.newresponse.GetProfileCompanyResponse
import com.inkubasi.hirehub.coreapp.domain.model.User
import com.inkubasi.hirehub.coreapp.utils.Utils
import com.inkubasi.hirehub.coreapp.utils.bottomsheet.CompanyAddBottomSheet
import com.inkubasi.hirehub.coreapp.utils.bottomsheet.UploadPhotoBottomSheet
import com.inkubasi.hirehub.databinding.FragmentProfileCompanyBinding
import com.inkubasi.hirehub.presentation.company.dashboard.profile.menu.edit.EditProfileCompanyActivity
import com.inkubasi.hirehub.presentation.company.dashboard.profile.menu.setting.SettingCompanyActivity
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileCompanyFragment : Fragment(){

    private lateinit var binding: FragmentProfileCompanyBinding
    private val profileCompanyViewModel : ProfileCompanyViewModel by viewModel()
    private lateinit var user: User
    private lateinit var dialog : Dialog
    private var resultCompanyResponse :GetProfileCompanyResponse? = null
    private var token : String? = null
    private var companyAddBottomSheet: CompanyAddBottomSheet? = null
    private var addPhotoProfileBottomSheet: UploadPhotoBottomSheet? = null
    private lateinit var multipartBody: MultipartBody.Part


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentProfileCompanyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog = Dialog(requireContext())


        bindViewModels()
        bindViewEvents()
    }

    private fun bindViewEvents() {
        with(binding){
            buttonSettings.setOnClickListener {
                val intent = Intent(requireContext(), SettingCompanyActivity::class.java)
                startActivity(intent)
            }
            buttonEdit.setOnClickListener {
                val intent = Intent(requireContext(), EditProfileCompanyActivity::class.java)
                intent.putExtra(EditProfileCompanyActivity.EXTRA_EDIT, resultCompanyResponse)
                startActivity(intent)
            }
            ivProfileCompany.setOnClickListener {
                showProfileUploadBottomSheet()
            }
        }
    }

    private fun bindViewModels() {
        profileCompanyViewModel.getUser().observe(this){
            user = it
            token = it.token
            profileCompanyViewModel.getProfileCompanyByUsername(user.token)
        }
        profileCompanyViewModel.state.observe(this){
            if (it.isLoading){
                Utils.showLoading(dialog, "Loading profile")
            }
            if (it.error.isNotEmpty()){
                Utils.hideLoading(dialog)
                resultCompanyResponse = null
            }
            if (it.profile != null){
                Utils.hideLoading(dialog)
                Glide.with(requireContext())
                    .load(it.profile.data?.company?.imageUrl ?: R.drawable.profile_add)
                    .into(binding.ivProfileCompany)
                if (it.profile.data?.company?.name == null) {
                    binding.svProfile.visibility = View.GONE
                    binding.cvEmptyProfile.visibility = View.VISIBLE
                    showProfileAddBottomSheet()
                } else {
                    binding.cvEmptyProfile.visibility = View.GONE
                    binding.svProfile.visibility = View.VISIBLE
                    resultCompanyResponse = it.profile
                    setProfile(it.profile)
                }
            }
        }

        profileCompanyViewModel.loadingState.observe(this){
            if (it) Utils.showLoading(dialog, "Loading..")
            else Utils.hideLoading(dialog)
        }

        profileCompanyViewModel.successPhoto.observe(this){
            Glide.with(requireContext())
                .load(it)
                .into(binding.ivProfileCompany)
            Utils.showToastSuccess(requireContext(), "Sukses Upload Image")
        }
    }

    private fun showProfileUploadBottomSheet() {
        addPhotoProfileBottomSheet = null
        addPhotoProfileBottomSheet = UploadPhotoBottomSheet.newInstance(
            onClickGallery = {
                //open gallery
                startGallery()
            },
           ""
        )
        addPhotoProfileBottomSheet?.show(parentFragmentManager, UploadPhotoBottomSheet.TAG)
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, getString(R.string.choose_picture))
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            val selectedImg = result.data?.data as Uri
            selectedImg.let { uri ->
                val myFile = Utils.uriToFile(uri, requireContext())
                val reduce = Utils.reduceFileImage(myFile)
                val requestImageFile = reduce.asRequestBody("image/jpeg".toMediaTypeOrNull())
                multipartBody =  MultipartBody.Part.createFormData("imageFile", "imageFile", requestImageFile)
                token?.let {
                    profileCompanyViewModel.uploadPhotoProfile(it, multipartBody)
                }
            }
        }
    }

    private fun showProfileAddBottomSheet() {
        companyAddBottomSheet = null
        companyAddBottomSheet = CompanyAddBottomSheet.newInstance(
            onClickData = {
                companyAddBottomSheet?.dismiss()
                val intent = Intent(requireContext(), EditProfileCompanyActivity::class.java)
                startActivity(intent)
            }
        )
        companyAddBottomSheet?.show(parentFragmentManager, CompanyAddBottomSheet.TAG)
    }


    private fun setProfile(profile: GetProfileCompanyResponse) {
        binding.tvCompany.text = profile.data?.company?.name
        binding.headOfficeCompanyDesc.text = profile.data?.company?.office
        binding.tvLocation.text = profile.data?.company?.location
        binding.websiteCompanyDesc.text = profile.data?.company?.webUrl
        binding.employeeCompanyDesc.text = profile.data?.company?.employee
        //not implement yet
        binding.aboutCompanyDesc.text = profile.data?.company?.description
        binding.industryCompanyDesc.text = profile.data?.company?.name
    }
}