package com.inkubasi.hirehub.presentation.applicant.register

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.inkubasi.hirehub.R
import com.inkubasi.hirehub.coreapp.utils.Utils
import com.inkubasi.hirehub.databinding.ActivityRegisterApplicantBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterApplicantActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterApplicantBinding
    private val registerApplicantViewModel: RegisterApplicantViewModel by viewModel()
    private lateinit var dialog: Dialog
    private var policyChecked: Boolean = false
    private var passwordVisible: Boolean?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterApplicantBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        dialog = Dialog(this)

        val passwordEditText: EditText = findViewById(R.id.ad_password_signup)
        val toggleButton: ImageButton = findViewById(R.id.toggle_password)

        toggleButton.setOnClickListener{
            passwordVisible = passwordVisible?.not()?:false
            val passwordType = if (passwordVisible == true)
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            else
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD

            passwordEditText.inputType = passwordType
            toggleButton.setImageResource(if (passwordVisible == true) R.drawable.eye_closed_svgrepo_com else R.drawable.eye_svgrepo_com)
 }
        registerApplicantViewModel.state.observe(this) {
            if (it.isLoading) {
                Utils.showLoading(dialog, getString(R.string.proses_registrasi))
            }
            if (it.error.isNotBlank()){
                Utils.hideLoading(dialog)
                Utils.showToastError(
                    this,
                    getString(R.string.username_tidak_tersedia)
                )
            }
            if (it.success != null) {
                Utils.hideLoading(dialog)
                Utils.showToastSuccess(
                    this,
                    it.success.message.orEmpty()
                )

                finish()
            }
        }



        bindViewEvents()
    }

    private fun bindViewEvents() {
        binding.login.setOnClickListener {
            finish()
        }
        binding.buttonSignup.setOnClickListener {
            if (policyChecked) {
                val username = binding.adUsernameSignup.text.toString().trim()
                val password = binding.adPasswordSignup.text.toString().trim()
                if (username.isEmpty() && password.isEmpty()) {
                    binding.adUsernameSignup.error = getString(R.string.password_is_empty)
                    binding.adPasswordSignup.error = getString(R.string.username_is_empty)
                } else {
                    if (username.isEmpty()) {
                        binding.adUsernameSignup.error = getString(R.string.username_is_empty)
                    } else {
                        binding.adUsernameSignup.error = null
                    }
                    if (username.isEmailValid()) {
                        binding.adUsernameSignup.error = getString(R.string.username_is_email)
                    } else {
                        binding.adUsernameSignup.error = null
                    }
                    if (password.isEmpty()) {
                        binding.adPasswordSignup.error = getString(R.string.password_is_empty)
                    } else {
                        binding.adPasswordSignup.error = null
                    }
                }

                if (username.isNotEmpty() && password.isNotEmpty()) {
                    if (password.length <= 8) {
                        Utils.showToastError(
                            this,
                            getString(R.string.kata_sandi_kurang)
                        )
                    } else if (username.isEmailValid()) {
                        Utils.showToastError(
                            this,
                            getString(R.string.username_is_email)
                        )
                    } else {
                        registerApplicantViewModel.registerApplicant(username, password)
                    }
                }
            } else {
                Toast.makeText(this, getString(R.string.policy_description), Toast.LENGTH_SHORT)
                    .show()
            }
        }

        binding.cbRegister.setOnCheckedChangeListener { _, isChecked ->
            policyChecked = isChecked
        }

        binding.tvConsentRegister.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.termsfeed.com/live/7d71a836-4b75-43cb-9ec8-d43f8f5af2d7")
            )
            startActivity(intent)
        }
    }

    private fun String.isEmailValid(): Boolean {
        val emailRegex = Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
        return emailRegex.matches(this)
    }
}