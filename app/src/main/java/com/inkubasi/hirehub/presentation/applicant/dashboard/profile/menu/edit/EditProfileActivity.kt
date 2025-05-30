package com.inkubasi.hirehub.presentation.applicant.dashboard.profile.menu.edit

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.chip.Chip
import com.inkubasi.hirehub.R
import com.inkubasi.hirehub.coreapp.data.source.remote.newresponse.GetProfileApplicantResponse
import com.inkubasi.hirehub.coreapp.utils.DatePickerFragment
import com.inkubasi.hirehub.coreapp.utils.Utils
import com.inkubasi.hirehub.databinding.ActivityEditProfileBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class EditProfileActivity : AppCompatActivity(), DatePickerFragment.DialogDateListener {

    private lateinit var binding: ActivityEditProfileBinding
    private var profile: GetProfileApplicantResponse? = null
    private val editProfileViewModel: EditProfileViewModel by viewModel()
    private lateinit var token: String
    private lateinit var dialog: Dialog
    private val chipTextList = mutableListOf<String>()
    private var dueDateMillis: String? = null
    private val chipLanguageList = mutableListOf<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        dialog = Dialog(this)
        profile = intent.getParcelableExtra(EXTRA_EDIT)


        if (profile != null) setProfileEdit()

        bindViewModels()
        bindViewEvent()
    }

    private fun bindViewModels() {
        editProfileViewModel.getUser().observe(this){
            token = it.token
        }
        editProfileViewModel.stateLoading.observe(this){
            if (it) Utils.showLoading(dialog, "Updating profile..")
            else Utils.hideLoading(dialog)
        }
        editProfileViewModel.stateError.observe(this){
            Utils.showToast(this, "Please, Enter valid personal data")
        }
        editProfileViewModel.stateSuccess.observe(this){
            Utils.showToastSuccess(this, "Success Updating Profile")
            finish()
        }
    }

    private fun bindViewEvent() {
        with(binding){
            buttonCancel.setOnClickListener {
                finish()
            }
            buttonSave.setOnClickListener {
                val name = addName.text.toString()
                val email = addEmail.text.toString()
                val field = addField.text.toString()
                val salaryMin = addSalaryMin.text.toString()
                val location = addLocation.text.toString()
                val degree = addDegree.text.toString()
                val phoneNumber = addPhoneNumber.text.toString()
                val description = addDescription.text.toString()
                val institution = addInstitution.text.toString()

                if (name.isNotEmpty() && email.isNotEmpty() && dueDateMillis.toString()
                        .isNotEmpty() &&
                    chipLanguageList.isNotEmpty() && field.isNotEmpty() && salaryMin.isNotEmpty() &&
                    location.isNotEmpty() && degree.isNotEmpty() && phoneNumber.isNotEmpty() &&
                    description.isNotEmpty() && chipTextList.isNotEmpty() && institution.isNotEmpty()
                ) {

                    editProfileViewModel.setProfileApplicant(
                        token, name, email, dueDateMillis.toString(), chipLanguageList, field, chipTextList,
                        salaryMin.toInt(), location, institution, degree, phoneNumber, description
                    )
                } else {
                    Utils.showToast(this@EditProfileActivity, "Please fill in all columns first")
                }

            }
            addSkillButton.setOnClickListener {
                if (addSkills.text.toString().isEmpty()) Utils.showToast(
                    this@EditProfileActivity,
                    "Please, Enter Skill first"
                )
                else {
                    addChip(addSkills.text.toString())
                    addSkills.setText("")
                }
            }

            addLanguageButton.setOnClickListener {
                if (addLanguages.text.toString()
                        .isEmpty()
                ) Utils.showToast(this@EditProfileActivity, "Please enter your language first")
                else {
                    addLanguage(addLanguages.text.toString())
                    addLanguages.setText("")
                }
            }
        }
    }

    private fun addLanguage(language: String){
        val chipLanguage = Chip(this)
        chipLanguage.isCloseIconVisible = true
        chipLanguage.text = language
        chipLanguageList.add(language)

        chipLanguage.setOnCloseIconClickListener{
            binding.chipGroup2.removeView(chipLanguage)
            chipLanguageList.remove(language)
        }
        binding.chipGroup2.addView(chipLanguage)
    }
    private fun addChip(skill: String) {
        val chip = Chip(this)
        chip.isCloseIconVisible = true
        chip.text = skill

        chipTextList.add(skill)

        chip.setOnCloseIconClickListener {
            binding.chipGroup.removeView(chip)
            chipTextList.remove(skill)
        }

        binding.chipGroup.addView(chip)
    }

    private fun setProfileEdit() {
        binding.addName.setText(profile?.data?.applicant?.name)
        binding.addTvDueDate.text =
            profile?.data?.applicant?.dateOfBirth?.let { Utils.formatDate(it) }
        dueDateMillis = profile?.data?.applicant?.dateOfBirth?.let { Utils.formatDateEdit(it) }
        binding.addEmail.setText(profile?.data?.applicant?.email)
       profile?.data?.applicant?.language?.forEach { language ->
           Chip(this).apply{
               chipLanguageList.add(language)
               text = language
               this.isCloseIconVisible = true
               this.setOnCloseIconClickListener{
                   binding.chipGroup2.removeView(this)
                   chipLanguageList.remove(language)
               }
               binding.chipGroup2.addView(this)
           }
       }
        binding.addLocation.setText(profile?.data?.applicant?.location)
        binding.addSalaryMin.setText(profile?.data?.applicant?.salaryMin.toString())
        binding.addPhoneNumber.setText(Utils.keepOnlyDigits(profile?.data?.applicant?.phone.toString()))
        binding.addDegree.setText(profile?.data?.applicant?.degree)
        binding.addInstitution.setText(profile?.data?.applicant?.institution)
        binding.addField.setText(profile?.data?.applicant?.field)
        binding.addDescription.setText(profile?.data?.applicant?.summary)
        profile?.data?.applicant?.skills?.forEach { skill ->
            Chip(this).apply {
                chipTextList.add(skill)
                text = skill
                this.isCloseIconVisible = true
                this.setOnCloseIconClickListener {
                    binding.chipGroup.removeView(this)
                    chipTextList.remove(skill)
                }

                binding.chipGroup.addView(this)
            }
        }
    }

    companion object {
        const val EXTRA_EDIT = "extra_edit"
    }

    fun showDatePicker(view: View) {
        val dialogFragment = DatePickerFragment()
        dialogFragment.show(supportFragmentManager, "datePicker")
    }

    override fun onDialogDateSet(tag: String?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        findViewById<TextView>(R.id.add_tv_due_date).text = dateFormat.format(calendar.time)
        dueDateMillis = dateFormat.format(calendar.time)
        Log.d("[DATE]", dateFormat.toString())
    }
}