package com.example.pocpreload

import android.annotation.SuppressLint
import android.content.Context
import android.webkit.WebView
import com.example.pocpreload.WebViewLogger.Companion.attachClientLogger

object WebViewFactory {
    @SuppressLint("SetJavaScriptEnabled")
    fun create(url: String, context: Context, onLoaded: () -> Unit = {}) = WebView(context).apply {
        settings.javaScriptEnabled = true
        attachClientLogger(onLoaded)
        loadUrl(url)
    }
}

object PreloadedWebView {
    private val webViewMap: MutableMap<String, WebView> = mutableMapOf()

    fun preload(url: String, context: Context, onLoaded: () -> Unit) {
        if (url in webViewMap) return // don't load new
        val webView = WebViewFactory.create(url, context, onLoaded)
        webViewMap[url] = webView
    }

    fun get(url: String): WebView {
        if (url !in webViewMap) throw IllegalStateException("Webview not exist")
        return webViewMap[url] ?: throw IllegalStateException("Webview is null")
    }

    fun clear() = webViewMap.clear()
}