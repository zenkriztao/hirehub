package com.inkubasi.hirehub.presentation.company.dashboard.profile.menu.edit

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.inkubasi.hirehub.coreapp.data.source.remote.newresponse.GetProfileCompanyResponse
import com.inkubasi.hirehub.coreapp.utils.Utils
import com.inkubasi.hirehub.databinding.ActivityEditProfileCompanyBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditProfileCompanyActivity : AppCompatActivity() {

    private lateinit var binding : ActivityEditProfileCompanyBinding
    private val editProfileViewModel : EditProfileCompanyViewModel by viewModel()
    private var profile : GetProfileCompanyResponse? = null
    private lateinit var token : String
    private lateinit var dialog : Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileCompanyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        dialog = Dialog(this)
        profile = intent.getParcelableExtra(EXTRA_EDIT)

        editProfileViewModel.getUser().observe(this){
            token = it.token
        }

        if (profile != null){
            setProfileCompanyEdit()
        }

        bindViewEvents()
        bindViewModels()
    }

    private fun bindViewModels() {
        editProfileViewModel.successEdit.observe(this){
            Utils.showToastSuccess(this@EditProfileCompanyActivity, "Success Updated Company")
            finish()
        }
        editProfileViewModel.loadingState.observe(this){
            if (it) Utils.showLoading(dialog, "Update company..")
            else Utils.hideLoading(dialog)
        }
    }

    private fun bindViewEvents() {
        with(binding){
            buttonCancel.setOnClickListener {
                finish()
            }
            buttonSave.setOnClickListener {
                val name = binding.addName.text.toString().trim()
                val location = binding.addLocation.text.toString().trim()
                val headOffice = binding.addHeadOffice.text.toString().trim()
                val website = binding.addWebsite.text.toString().trim()
                val industry = binding.addIndustry.text.toString().trim()
                val employeeSize = binding.addEmployeeSize.text.toString().trim()
                val description = binding.addDescription.text.toString().trim()

                if (name.isNotEmpty() && location.isNotEmpty() && headOffice.isNotEmpty() && website.isNotEmpty() &&
                    industry.isNotEmpty() && employeeSize.isNotEmpty() && description.isNotEmpty()) {
                    editProfileViewModel.setProfileCompany(token, name, location, headOffice, website, industry, employeeSize, description)
                } else {
                    Utils.showToast(this@EditProfileCompanyActivity, "Harap isi semua kolom.")
                }
            }

        }
    }

    private fun setProfileCompanyEdit() {
        binding.addName.setText(profile?.data?.company?.name)
        binding.addLocation.setText(profile?.data?.company?.location)
        binding.addHeadOffice.setText(profile?.data?.company?.office)
        binding.addWebsite.setText(profile?.data?.company?.webUrl)
        binding.addIndustry.setText(profile?.data?.company?.name)
        binding.addEmployeeSize.setText(profile?.data?.company?.employee)
        binding.addDescription.setText(profile?.data?.company?.name)

    }

    companion object{
        const val EXTRA_EDIT = "extra_edit"
    }
}