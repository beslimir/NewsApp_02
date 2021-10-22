package com.example.newsapp_02.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.newsapp_02.R
import com.example.newsapp_02.databinding.FragmentInfoBinding
import com.example.newsapp_02.presentation.view_model.NewsViewModel
import com.google.android.material.snackbar.Snackbar

class InfoFragment : Fragment() {

    private lateinit var fragmentInfoBinding: FragmentInfoBinding
    private lateinit var newsViewModel: NewsViewModel
    val args: InfoFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newsViewModel = (activity as MainActivity).viewModel
        fragmentInfoBinding = FragmentInfoBinding.bind(view)
        fragmentInfoBinding.webView.apply {
            webViewClient = WebViewClient()
            loadUrl(args.article.link)
        }

        val openArticle = args.article
        fragmentInfoBinding.fabInfo.setOnClickListener {
            newsViewModel.saveNews(openArticle)
            Snackbar.make(view, "Article successfully saved.", Snackbar.LENGTH_SHORT).show()
        }

    }
}