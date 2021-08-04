package com.example.pocpreload

import android.graphics.Bitmap
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

class WebViewLogger(val onLoaded: () -> Unit) : WebViewClient() {

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        Log.i(TAG, "onPageStarted: $url")
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        Log.i(TAG, "onPageFinished: $url progress: ${view?.progress}")
        if (view?.progress == 100) {
            onLoaded()
        }
    }

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        Log.i(TAG, "shouldOverrideUrlLoading: ${request?.url}")
        return super.shouldOverrideUrlLoading(view, request)
    }

    companion object {
        val TAG = WebViewLogger::class.java.name

        fun WebView.attachClientLogger(onLoaded: () -> Unit) {
            this.webViewClient = WebViewLogger(onLoaded)
        }
    }
}