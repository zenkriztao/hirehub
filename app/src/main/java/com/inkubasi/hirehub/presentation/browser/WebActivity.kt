package com.inkubasi.hirehub.presentation.browser

import android.app.Dialog
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import androidx.webkit.WebViewCompat
import androidx.webkit.WebViewFeature
import com.inkubasi.hirehub.coreapp.utils.Utils
import com.inkubasi.hirehub.databinding.ActivityWebBinding

class WebActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebBinding

    private var currentAddress: String = ""
    private lateinit var dialog: Dialog


    private val url by lazy {
        intent.getStringExtra(URL)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dialog = Dialog(this)

        initView()
        loadUrl()
    }

    private fun initView() {
        CookieManager.getInstance().setAcceptCookie(true)
        binding.webView.apply {
            settings.apply {
                javaScriptEnabled = true
                setSupportMultipleWindows(false)
                // domStorageEnabled = true
                cacheMode = WebSettings.LOAD_NO_CACHE
                // mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                // allowContentAccess = true
            }
            webChromeClient = mWebChromeClient
            webViewClient = mWebViewClient
        }
        if (WebViewFeature.isFeatureSupported(WebViewFeature.WEB_MESSAGE_LISTENER)) {
            WebViewCompat.addWebMessageListener(
                binding.webView,
                JS_OBJECT_NAME,
                setOf(),
                mWebMessageListener
            )
        }
    }

    private val mWebMessageListener =
        WebViewCompat.WebMessageListener { view, message, sourceOrigin, isMainFrame, replyProxy ->
            Log.d("[WEB] [WEB_MESSAGE] ", "${message.data}")
        }

    private fun loadUrl() {
        url?.let {
            currentAddress = it
            binding.webView.loadUrl(it)
        }
    }


    private val mWebViewClient = object : WebViewClient() {
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            showLoadingWb()
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            showLoadingWb(false)
        }
    }

    private fun showLoadingWb(show: Boolean = true) {
        if (show) Utils.showLoading(dialog, "Memuat")
        else Utils.hideLoading(dialog)
    }

    /**
     * Web View Client (CHROME)
     */
    private val mWebChromeClient = object : WebChromeClient() {
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            setOnProgressChanged(newProgress)
        }

        override fun onReceivedTitle(view: WebView?, title: String?) {
            super.onReceivedTitle(view, title)
            setOnReceivedTitle(title.orEmpty())
        }
    }

    private fun setOnProgressChanged(newProgress: Int) {

    }

    private fun setOnReceivedTitle(title: String) {

    }

    companion object {
        const val URL = "WebActivity.URL"

        private const val JS_OBJECT_NAME = "Android"
    }

}