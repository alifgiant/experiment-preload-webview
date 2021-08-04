package com.example.pocpreload

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pocpreload.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val urlText get() = binding.etUrlInput.text.toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setButtons()
    }

    override fun onDestroy() {
        super.onDestroy()
        PreloadedWebView.clear()
    }

    private fun createNavIntent(usePreloadedWebview: Boolean) = WebActivity.createIntent(
        this,
        urlText,
        usePreloadedWebview
    )

    private fun setButtons() {
        binding.btnNavigateWithouPreload.setOnClickListener {
            startActivity(createNavIntent(false))
        }
        binding.btnPreload.setOnClickListener {
            if (urlText.isNullOrEmpty()) return@setOnClickListener
            PreloadedWebView.preload(urlText, applicationContext) {
                Log.i(TAG, "preload completed")
                Toast.makeText(this, "preload completed", Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnNavigateWithNewWebView.setOnClickListener {
            startActivity(createNavIntent(false))
        }
        binding.btnNavigateWithPassedWebView.setOnClickListener {
            startActivity(createNavIntent(true))
        }
    }

    companion object {
        val TAG = MainActivity::class.java.name
    }
}