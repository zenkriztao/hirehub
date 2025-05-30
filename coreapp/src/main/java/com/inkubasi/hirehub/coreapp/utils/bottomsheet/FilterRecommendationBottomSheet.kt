package com.inkubasi.hirehub.coreapp.utils.bottomsheet

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.inkubasi.hirehub.coreapp.BuildConfig
import com.inkubasi.hirehub.coreapp.R
import com.inkubasi.hirehub.coreapp.data.source.remote.request.ApplicantRecommenderRequest
import com.inkubasi.hirehub.coreapp.databinding.BottomSheetFilterBinding
import java.text.NumberFormat
import java.util.*

class FilterRecommendationBottomSheet(
    private val onClickData: (ApplicantRecommenderRequest) -> Unit,
) : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetFilterBinding
    private val chipTextList = mutableListOf<String>()
    private val chipLanguageList = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetFilterBinding.inflate(inflater, container, false)
        return binding.root
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
            addSkillButton.setOnClickListener {
                if (addSkills.text.toString().isEmpty()) Toast.makeText(
                    requireContext(),
                    "Please, Enter Skill first",
                    Toast.LENGTH_SHORT
                ).show()
                else {
                    addChip(addSkills.text.toString())
                    addSkills.setText("")
                }
            }

            addLanguageButton.setOnClickListener {
                if (addLanguages.text.toString().isEmpty()) Toast.makeText(
                    requireContext(),
                    "Please, enter your language first",
                    Toast.LENGTH_SHORT
                ).show()
                else {
                    addLanguage(addLanguages.text.toString())
                    addLanguages.setText("")
                }
            }
            btnApply.setOnClickListener {
                when {
                    chipTextList.isEmpty() -> {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.skill_cannot_empty),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    chipLanguageList.isEmpty() -> {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.language_cant_empty),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    tieMinimalUmur.text.toString().isEmpty() -> {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.min_age_cant_empty),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    tieMaksimalUmur.text.toString().isEmpty() -> {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.maks_age_cant_empty),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    tieToleransiUmur.text.toString().isEmpty() -> {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.tolerance_age_cant_empty),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    tieMinimalSalary.text.toString().isEmpty() -> {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.min_salary_cant_empty),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    tieMaksimalSalary.text.toString().isEmpty() -> {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.maks_salary_cant_empty),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    tieToleransiSalary.text.toString().isEmpty() -> {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.tolerance_salary_cant_empty),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    else -> {
                        onClickData.invoke(
                            ApplicantRecommenderRequest(
                                ageFilter = listOf(
                                    tieMinimalUmur.text.toString().toInt(),
                                    tieMaksimalUmur.text.toString().toInt()
                                ),
                                ageTolerance = tieToleransiUmur.text.toString().toInt(),
                                skillFilter = chipTextList,
                                languageFilter = chipLanguageList,
                                salaryFilter = listOf(
                                    salaryFormat(tieMinimalSalary.text.toString()),
                                    salaryFormat(tieMaksimalSalary.text.toString())
                                ),
                                salaryTolerance = salaryFormat(tieToleransiSalary.text.toString())
                            )
                        )
                        dismiss()
                    }
                }
            }

            tieMinimalSalary.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    // Do nothing
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    // Do nothing
                }

                override fun afterTextChanged(editable: Editable?) {
                    val originalText = editable.toString()

                    if (originalText.isNotEmpty()) {
                        val cleanString = originalText.replace("[^\\d]".toRegex(), "")
                        val parsed = cleanString.toLong()

                        val formatted =
                            NumberFormat.getNumberInstance(Locale.getDefault()).format(parsed)
                        tieMinimalSalary.removeTextChangedListener(this)
                        tieMinimalSalary.setText(formatted)
                        tieMinimalSalary.setSelection(formatted.length)
                        tieMinimalSalary.addTextChangedListener(this)
                    }
                }
            })
            tieMaksimalSalary.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    // Do nothing
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    // Do nothing
                }

                override fun afterTextChanged(editable: Editable?) {
                    val originalText = editable.toString()

                    if (originalText.isNotEmpty()) {
                        val cleanString = originalText.replace("[^\\d]".toRegex(), "")
                        val parsed = cleanString.toLong()

                        val formatted =
                            NumberFormat.getNumberInstance(Locale.getDefault()).format(parsed)
                        tieMaksimalSalary.removeTextChangedListener(this)
                        tieMaksimalSalary.setText(formatted)
                        tieMaksimalSalary.setSelection(formatted.length)
                        tieMaksimalSalary.addTextChangedListener(this)
                    }
                }
            })
            tieToleransiSalary.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    // Do nothing
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    // Do nothing
                }

                override fun afterTextChanged(editable: Editable?) {
                    val originalText = editable.toString()

                    if (originalText.isNotEmpty()) {
                        val cleanString = originalText.replace("[^\\d]".toRegex(), "")
                        val parsed = cleanString.toLong()

                        val formatted =
                            NumberFormat.getNumberInstance(Locale.getDefault()).format(parsed)
                        tieToleransiSalary.removeTextChangedListener(this)
                        tieToleransiSalary.setText(formatted)
                        tieToleransiSalary.setSelection(formatted.length)
                        tieToleransiSalary.addTextChangedListener(this)
                    }
                }
            })
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        (super.onCreateDialog(savedInstanceState) as BottomSheetDialog).apply {
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            setCancelable(false)
            setCanceledOnTouchOutside(false)
        }

    private fun addLanguage(language: String) {
        val chipLanguage = Chip(requireContext())
        chipLanguage.isCloseIconVisible = true
        chipLanguage.text = language
        chipLanguageList.add(language)

        chipLanguage.setOnCloseIconClickListener {
            binding.chipGroup2.removeView(chipLanguage)
            chipLanguageList.remove(language)
        }
        binding.chipGroup2.addView(chipLanguage)
    }

    private fun addChip(skill: String) {
        val chip = Chip(requireContext())
        chip.isCloseIconVisible = true
        chip.text = skill

        chipTextList.add(skill)

        chip.setOnCloseIconClickListener {
            binding.chipGroup.removeView(chip)
            chipTextList.remove(skill)
        }

        binding.chipGroup.addView(chip)
    }

    private fun salaryFormat(formattedText: String): Int {
        val cleanString = formattedText.replace("[^\\d]".toRegex(), "")
        return cleanString.toInt()
    }

    companion object {
        val TAG
            get() = "TAG.${BuildConfig.LIBRARY_PACKAGE_NAME}.${FilterRecommendationBottomSheet::class.java.simpleName}"

        fun newInstance(
            onClickData: (ApplicantRecommenderRequest) -> Unit
        ) = FilterRecommendationBottomSheet(
            onClickData
        )
    }
}