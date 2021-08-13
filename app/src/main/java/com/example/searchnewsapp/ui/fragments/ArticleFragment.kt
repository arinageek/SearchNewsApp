package com.example.searchnewsapp.ui.fragments

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.mynewsapp.api.Article
import com.example.searchnewsapp.R
import com.example.searchnewsapp.databinding.ArticleFragmentBinding
import com.example.searchnewsapp.ui.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.article_fragment.*

@AndroidEntryPoint
class ArticleFragment : Fragment(R.layout.article_fragment) {

    private val args by navArgs<ArticleFragmentArgs>()
    private val newsViewModel by viewModels<NewsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val article = args.article
        val binding = ArticleFragmentBinding.bind(view)
        article?.let{ article ->
            webView.apply{
                webViewClient = WebViewClient()
                loadUrl(article.url)
            }
            binding.fab.setOnClickListener{
                newsViewModel.upsertArticle(article)
                Snackbar.make(view, "Article saved successfully", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}