package com.example.webviewchromeclient

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar.max = 100

        val URL = "https://www.google.com/"

        webView.loadUrl(URL)
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient(); //sebelum

        //jika menggunakan 2 webclient
        webView.webViewClient =  object : WebViewClient() {

            //start search akan tampil loading
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                progressBar.visibility = View.VISIBLE
                imgView.visibility = View.VISIBLE
                super.onPageStarted(view, url, favicon)
            }

            //finish bar jadi GONE
            override fun onPageFinished(view: WebView?, url: String?) {
                progressBar.visibility = View.GONE
                imgView.visibility = View.GONE
                swipeRefreshLayout.isRefreshing = false
                super.onPageFinished(view, url)
            }
        }

        webView.webChromeClient = object : WebChromeClient() {

            //progress loading
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                progressBar.setProgress(newProgress)
            }

            //title
            override fun onReceivedTitle(view: WebView?, title: String?) {
                super.onReceivedTitle(view, title)
                supportActionBar?.setTitle(title)
            }

            //icon
            override fun onReceivedIcon(view: WebView?, icon: Bitmap?) {
                super.onReceivedIcon(view, icon)
                imgView.setImageBitmap(icon)
            }
        }

        //swipe
        swipeRefreshLayout.setOnRefreshListener {

            webView.reload()
        }
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            finish()
        }
    }
}
