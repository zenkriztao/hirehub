package com.inkubasi.hirehub.presentation.applicant.dashboard.profile.detail.cv

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.OpenableColumns
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.inkubasi.hirehub.coreapp.utils.Utils
import com.inkubasi.hirehub.coreapp.utils.Utils.showLoading
import com.inkubasi.hirehub.databinding.ActivityUploadCvactivityBinding
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.koin.androidx.viewmodel.ext.android.viewModel

class UploadCVActivity : AppCompatActivity() {

    private lateinit var binding : ActivityUploadCvactivityBinding
    private var pdfFile: Uri? = null
    private lateinit var dialog : Dialog
    private lateinit var token : String
    private val uploadCvViewModel : UploadCvViewModel by viewModel()


    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val data: Intent? = result.data
            val selectedFileUri = data?.data
            if (selectedFileUri != null) {
                val contentResolver = contentResolver
                val cursor = contentResolver.query(selectedFileUri, null, null, null, null)
                try {
                    if (cursor != null && cursor.moveToFirst()) {
                        val displayNameColumnIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)

                        if (displayNameColumnIndex != -1) {
                            val displayName = cursor.getString(displayNameColumnIndex)
                            pdfFile = selectedFileUri
                            binding.buttonUpload.isEnabled = true
                            binding.tvNameCv.text = displayName
                            binding.cvComplete.visibility = View.VISIBLE
                            binding.cvEmptyCv.visibility = View.GONE
                        }
                    }
                } finally {
                    cursor?.close()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadCvactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dialog = Dialog(this)

        supportActionBar?.hide()

        bindViewModel()
        bindViewEvents()
    }

    private fun bindViewModel() {
        uploadCvViewModel.getUser().observe(this){
            token = it.token
        }
        uploadCvViewModel.loadingState.observe(this){
            if (it) showLoading(dialog, "Update profile..")
            else Utils.hideLoading(dialog)
        }
        uploadCvViewModel.successUpload.observe(this){
            Toast.makeText(this@UploadCVActivity, "Berhasil Upload", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun bindViewEvents() {
        with(binding){
            buttonCv.setOnClickListener {
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = "application/pdf"
                startForResult.launch(intent)
            }
            buttonUpload.setOnClickListener {
                pdfFile?.let { it ->
                    val multiPart = uriToMultipartBodyPart(it)
                    multiPart?.let {pdf ->
                        uploadCvViewModel.uploadCV(token, pdf)
                    }
                }
            }
        }
    }

    private fun uriToMultipartBodyPart(uri: Uri): MultipartBody.Part? {
        val contentResolver = applicationContext.contentResolver
        val inputStream = contentResolver.openInputStream(uri)
        val bytes = inputStream?.readBytes()
        val requestBody = bytes?.let {
            RequestBody.create("application/pdf".toMediaTypeOrNull(),
                it
            )
        }
        return requestBody?.let { MultipartBody.Part.createFormData("pdfFile", "pdfFile", it) }
    }

}