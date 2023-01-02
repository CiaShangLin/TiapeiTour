package com.shang.taipeitour.view.webview

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.shang.taipeitour.R
import com.shang.taipeitour.databinding.FragmentWebViewBinding
import java.util.Stack

class WebViewFragment : Fragment() {

    companion object {
        const val TITLE = "TITLE"
        const val URL = "URL"
    }

    private lateinit var mBinding: FragmentWebViewBinding
    private val mUrlStack = Stack<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_web_view,
            container,
            false
        )
        mBinding.url = arguments?.getString(URL, "")
        mBinding.title = arguments?.getString(TITLE, "")
        mUrlStack.add(mBinding.url)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            if (mUrlStack.size == 1) {
                isEnabled = false
                requireActivity().onBackPressed()
            } else {
                mUrlStack.pop()
                val url = mUrlStack.peek()
                mBinding.webView.loadUrl(url)
            }
        }
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = mBinding.title
        mBinding.webView.webViewClient = TourWebClient()
        mBinding.webView.webChromeClient = TourWebChromeClient()
        mBinding.webView.settings.useWideViewPort = true
        mBinding.webView.settings.javaScriptEnabled = true
        mBinding.webView.settings.javaScriptCanOpenWindowsAutomatically = true
        mBinding.webView.settings.setSupportZoom(true)

        mBinding.webView.loadUrl(mBinding.url ?: "")
    }

    inner class TourWebClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            request?.url?.let {
                mUrlStack.add(it.toString())
                view?.loadUrl(it.toString())
                return@let
            }
            return super.shouldOverrideUrlLoading(view, request)
        }
    }

    inner class TourWebChromeClient : WebChromeClient() {
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            mBinding.progressBar.visibility = if (newProgress >= 100) {
                View.GONE
            } else {
                View.VISIBLE
            }
        }
    }
}