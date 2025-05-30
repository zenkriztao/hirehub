package com.inkubasi.hirehub.presentation.company.login

import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.InputType
import android.text.method.PasswordTransformationMethod
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.inkubasi.hirehub.R
import com.inkubasi.hirehub.coreapp.domain.model.User
import com.inkubasi.hirehub.coreapp.utils.Utils
import com.inkubasi.hirehub.coreapp.utils.bottomsheet.CompanyValidationBottomSheet
import com.inkubasi.hirehub.databinding.ActivityLoginCompanyBinding
import com.inkubasi.hirehub.presentation.company.dashboard.MainCompanyActivity
import com.inkubasi.hirehub.presentation.company.register.RegisterCompanyActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginCompanyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginCompanyBinding
    private val loginCompanyViewModel: LoginCompanyViewModel by viewModel()
    private lateinit var dialog: Dialog
    private lateinit var username: String
    private lateinit var tokenApi: String
    private lateinit var userId: String
    private var passwordVisible: Boolean?= null

    private var companyValidationBottomSheet: CompanyValidationBottomSheet? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_company)
        binding = ActivityLoginCompanyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        dialog = Dialog(this)
        binding.adPasswordSignin.transformationMethod = PasswordTransformationMethod.getInstance()

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

        bindViewModels()
        bindViewEvents()
    }

    private fun bindViewModels() {
        loginCompanyViewModel.stateError.observe(this) {
            Utils.showToastError(this, it)
        }
        loginCompanyViewModel.stateLoading.observe(this) {
            if (it) {
                Utils.showLoading(dialog, getString(R.string.proses_login))
            } else {
                Utils.hideLoading(dialog)
            }
        }
        loginCompanyViewModel.stateSuccess.observe(this) {
            if (it.data?.user?.roleName == "company") {
                if (it.data?.user?.isValid == 1) {
                    tokenApi = it.data?.token.orEmpty()
                    userId = it.data?.user?.id.toString()
                    loginCompanyViewModel.getStreamToken(tokenApi)
                } else {
                    showBottomSheet()
                }
            } else {
                Utils.showToastError(
                    this,
                    getString(R.string.this_is_applicant_account)
                )
            }
        }
        loginCompanyViewModel.stateToken.observe(this) {
            val user = User(
                name = username,
                token = tokenApi,
                isLogin = true,
                isApplicant = false,
                id = userId,
                tokenStream = it
            )
            loginCompanyViewModel.saveUser(user)
            Utils.showToastSuccess(
                this,
                getString(R.string.login_berhasil)
            )
            val intent = Intent(this, MainCompanyActivity::class.java)
            intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
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
                loginCompanyViewModel.loginCompany(username, password)
            }

        }

        binding.register.setOnClickListener {
            Intent(this, RegisterCompanyActivity::class.java).also {
                startActivity(it)
            }
        }
    }

    private fun showBottomSheet(){
        companyValidationBottomSheet = CompanyValidationBottomSheet.newInstance(
            onClickData = {
                try {
                    val emailIntent = Intent(Intent.ACTION_SENDTO)
                    emailIntent.data = Uri.parse("mailto:hirehub.id@gmail.com")
                    startActivity(emailIntent)

                }catch (e: ActivityNotFoundException){
                    Toast.makeText(this,
                        getString(R.string.please_login_your_gmail_first), Toast.LENGTH_SHORT).show()
                }

            }
        )
        companyValidationBottomSheet?.show(supportFragmentManager,CompanyValidationBottomSheet.TAG)
    }
}


