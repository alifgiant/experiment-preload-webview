package com.example.pocpreload

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import com.example.pocpreload.databinding.ActivityWebBinding

class WebActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWebBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "WebView"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        attachWebView()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item);
        }
    }

    private fun createWebView(url: String, usePreloadedWebview: Boolean): WebView {
        return if (usePreloadedWebview) {
            PreloadedWebView.get(url)
        } else {
            WebViewFactory.create(url, this) {
                Log.i(TAG, "normal load completed")
            }
        }
    }

    private fun attachWebView() {
        val url = intent.getStringExtra(EXTRA_KEY_URL)
        val usePreload = intent.getBooleanExtra(EXTRA_KEY_USE_PRELOAD_WEBVIEW, false)
        Log.i(TAG, "url: $url, usePreload: $usePreload")

        if (url.isNullOrBlank()) return
        val view = createWebView(url, usePreload)
        binding.root.addView(view)
    }

    companion object {
        val TAG = WebActivity::class.java.name

        const val EXTRA_KEY_URL = "key_url"
        const val EXTRA_KEY_USE_PRELOAD_WEBVIEW = "key_preload"

        fun createIntent(context: Context, url: String, usePreloadedWebview: Boolean): Intent {
            return Intent(context, WebActivity::class.java).apply {
                putExtra(EXTRA_KEY_URL, url)
                putExtra(EXTRA_KEY_USE_PRELOAD_WEBVIEW, usePreloadedWebview)
            }
        }
    }
}