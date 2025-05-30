package com.inkubasi.hirehub.presentation.applicant.dashboard.profile

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
import com.inkubasi.hirehub.coreapp.data.source.remote.newresponse.GetProfileApplicantResponse
import com.inkubasi.hirehub.coreapp.domain.model.User
import com.inkubasi.hirehub.coreapp.utils.Utils
import com.inkubasi.hirehub.coreapp.utils.Utils.reduceFileImage
import com.inkubasi.hirehub.coreapp.utils.Utils.uriToFile
import com.inkubasi.hirehub.coreapp.utils.bottomsheet.ProfileAddBottomSheet
import com.inkubasi.hirehub.coreapp.utils.bottomsheet.UploadPhotoBottomSheet
import com.inkubasi.hirehub.databinding.FragmentProfileApplicantBinding
import com.inkubasi.hirehub.presentation.applicant.dashboard.profile.detail.AboutMeActivity
import com.inkubasi.hirehub.presentation.applicant.dashboard.profile.detail.AboutMeActivity.Companion.EXTRA_ABOUT
import com.inkubasi.hirehub.presentation.applicant.dashboard.profile.detail.cv.UploadCVActivity
import com.inkubasi.hirehub.presentation.applicant.dashboard.profile.menu.edit.EditProfileActivity
import com.inkubasi.hirehub.presentation.applicant.dashboard.profile.menu.edit.EditProfileActivity.Companion.EXTRA_EDIT
import com.inkubasi.hirehub.presentation.applicant.dashboard.profile.menu.setting.SettingsApplicantActivity
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileApplicantFragment : Fragment() {

    private lateinit var binding: FragmentProfileApplicantBinding
    private val profileApplicantViewModel : ProfileApplicantViewModel by viewModel()
    private lateinit var user: User
    private lateinit var dialog : Dialog
    private var token : String? = null
    private var profileAddBottomSheet: ProfileAddBottomSheet? = null
    private var addPhotoProfileBottomSheet: UploadPhotoBottomSheet? = null
    private var imageUrl : String? = null
    private lateinit var multipartBody: MultipartBody.Part
    private var profile : GetProfileApplicantResponse? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileApplicantBinding.inflate(inflater, container, false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog = Dialog(requireContext())

        profileApplicantViewModel.getUser().observe(this){
            user = it
            token = it.token
            profileApplicantViewModel.getProfileApplicantByUsername(user.token)
        }

        bindViewModels()
        binding.ivProfileApplicant.setOnClickListener {
            showProfileUploadBottomSheet()
        }

        binding.buttonSettings.setOnClickListener {
            val intent = Intent(requireContext(), SettingsApplicantActivity::class.java)
            startActivity(intent)
        }

        binding.buttonCv.setOnClickListener {
            val intent = Intent(requireContext(), UploadCVActivity::class.java)
            startActivity(intent)
        }

        binding.buttonAboutme.setOnClickListener {
            val intent = Intent(requireContext(), AboutMeActivity::class.java)
            intent.putExtra(EXTRA_ABOUT, profile)
            startActivity(intent)
        }

        binding.buttonEdit.setOnClickListener {
            val intent = Intent(requireContext(), EditProfileActivity::class.java)
            intent.putExtra(EXTRA_EDIT, profile)
            startActivity(intent)
        }

        binding.buttonSettingsState.setOnClickListener {
            val intent = Intent(requireContext(), SettingsApplicantActivity::class.java)
            startActivity(intent)
        }

    }

    private fun bindViewModels() {
        profileApplicantViewModel.state.observe(this){
            if (it.isLoading){
                Utils.showLoading(dialog, "Loading profile")
            }
            if (it.error.isNotEmpty()) {
                Utils.hideLoading(dialog)
                profile = null
            }
            if (it.profile?.success == true) {
                Utils.hideLoading(dialog)
                Glide.with(requireContext())
                    .load(it.profile.data?.applicant?.imageUrl ?: R.drawable.profile_add)
                    .into(binding.ivProfileApplicant)
                if (it.profile.data?.applicant?.name == null) {
                    binding.cvProfile.visibility = View.GONE
                    binding.cvEmptyProfile.visibility = View.VISIBLE
                    profile = null
                    showProfileAddBottomSheet()
                } else {
                    binding.cvProfile.visibility = View.VISIBLE
                    binding.cvEmptyProfile.visibility = View.GONE
                    setProfile(it.profile)
                }
            }
        }

        profileApplicantViewModel.stateProfile.observe(this){
            if (it.isLoading){
                Utils.showLoading(dialog, "Uploading photo profile")
            }
            if (it.error != null){
                Utils.hideLoading(dialog)
                Utils.showToastError(requireContext(), it.error)
            }
            if (it.profileImage != null){
                Glide.with(requireContext())
                    .load(it.profileImage)
                    .into(binding.ivProfileApplicant)
                Utils.hideLoading(dialog)
                Utils.showToastSuccess(requireContext(), "Upload Photo success")
            }
        }
    }

    private fun showProfileAddBottomSheet() {
        profileAddBottomSheet = null
        profileAddBottomSheet = ProfileAddBottomSheet.newInstance(
           onClickCv = {
               profileAddBottomSheet?.dismiss()
               val intent = Intent(requireContext(), UploadCVActivity::class.java)
               startActivity(intent)
           },
            onClickData = {
                profileAddBottomSheet?.dismiss()
                val intent = Intent(requireContext(), EditProfileActivity::class.java)
                startActivity(intent)
            }
        )
        profileAddBottomSheet?.show(parentFragmentManager, ProfileAddBottomSheet.TAG)
    }

    private fun showProfileUploadBottomSheet() {
        addPhotoProfileBottomSheet = null
        addPhotoProfileBottomSheet = UploadPhotoBottomSheet.newInstance(
            onClickGallery = {
                //open gallery
                startGallery()
            },
            imageUrl.orEmpty()
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
                val myFile = uriToFile(uri, requireContext())
                val reduce = reduceFileImage(myFile)
                val requestImageFile = reduce.asRequestBody("image/jpeg".toMediaTypeOrNull())
                multipartBody =  MultipartBody.Part.createFormData("imageFile", "imageFile", requestImageFile)
                token?.let {
                    profileApplicantViewModel.uploadPhotoProfile(it, multipartBody)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        profileAddBottomSheet?.dismiss()
        addPhotoProfileBottomSheet?.dismiss()
    }


    private fun setProfile(profile: GetProfileApplicantResponse) {
        binding.tvName.text = profile.data?.applicant?.name
        binding.tvRole.text = profile.data?.applicant?.field
        imageUrl = profile.data?.applicant?.imageUrl
        this.profile = profile
    }

}