package com.inkubasi.hirehub.presentation.applicant.login

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.inkubasi.hirehub.R
import com.inkubasi.hirehub.coreapp.domain.model.User
import com.inkubasi.hirehub.coreapp.utils.Utils
import com.inkubasi.hirehub.databinding.ActivityLoginApplicantBinding
import com.inkubasi.hirehub.presentation.applicant.dashboard.MainApplicantActivity
import com.inkubasi.hirehub.presentation.applicant.register.RegisterApplicantActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginApplicantActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginApplicantBinding
    private val loginApplicantViewModel: LoginApplicantViewModel by viewModel()
    private lateinit var dialog: Dialog
    private lateinit var username: String
    private lateinit var tokenApi: String
    private lateinit var userId: String
    private var passwordVisible: Boolean?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginApplicantBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        dialog = Dialog(this)

        val passwordEditText: EditText = findViewById(R.id.ad_password_signin)
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

        bindViewEvents()
        bindViewModels()
    }

    private fun bindViewEvents() {
        binding.buttonSignin.setOnClickListener {
            username = binding.adUsernameSignin.text.toString().trim()
            val password = binding.adPasswordSignin.text.toString().trim()
            if (username.isEmpty() && password.isEmpty()) {
                binding.adPasswordSignin.error = getString(R.string.password_is_empty)
                binding.adUsernameSignin.error = getString(R.string.username_is_empty)
            } else {
                if (username.isEmpty()) {
                    binding.adUsernameSignin.error = getString(R.string.username_is_empty)
                } else {
                    binding.adUsernameSignin.error = null
                }
                if (password.isEmpty()) {
                    binding.adPasswordSignin.error = getString(R.string.password_is_empty)
                } else {
                    binding.adPasswordSignin.error = null
                }
            }

            if (username.isNotEmpty() && password.isNotEmpty()) {
                loginApplicantViewModel.loginApplicant(username, password)
            }

        }

        binding.register.setOnClickListener {
            Intent(this, RegisterApplicantActivity::class.java).also {
                startActivity(it)
            }
        }
    }

    private fun bindViewModels() {
        loginApplicantViewModel.stateError.observe(this) {
            Utils.showToastError(this, it)
        }
        loginApplicantViewModel.stateLoading.observe(this) {
            if (it) {
                Utils.showLoading(dialog, getString(R.string.proses_login))
            } else {
                Utils.hideLoading(dialog)
            }
        }
        loginApplicantViewModel.stateSuccess.observe(this) {
            if (it.data?.user?.roleName == "applicant") {
                tokenApi = it.data?.token.orEmpty()
                userId = it.data?.user?.id.toString()
                loginApplicantViewModel.getStreamToken(tokenApi)
            } else {
                Utils.hideLoading(dialog)
                Utils.showToastError(this, getString(R.string.this_is_company_account))

            }
        }
        loginApplicantViewModel.stateToken.observe(this) {
            val user = User(
                name = username,
                token = tokenApi,
                id = userId,
                isLogin = true,
                isApplicant = true,
                tokenStream = it
            )
            loginApplicantViewModel.saveUser(user)
            Utils.hideLoading(dialog)
            Utils.showToastSuccess(this, getString(R.string.login_berhasil))
            val intent = Intent(this, MainApplicantActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}