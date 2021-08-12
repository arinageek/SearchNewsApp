package com.example.searchnewsapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.searchnewsapp.R
import com.example.searchnewsapp.adapters.NewsAdapter
import com.example.searchnewsapp.databinding.BreakingNewsFragmentBinding
import com.example.searchnewsapp.ui.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BreakingNewsFragment : Fragment(R.layout.breaking_news_fragment) {

    private lateinit var binding: BreakingNewsFragmentBinding
    private val newsViewModel by viewModels<NewsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = BreakingNewsFragmentBinding.bind(view)

        val newsAdapter = NewsAdapter()

        binding.rvBreakingNews.apply{
            setHasFixedSize(true)
            adapter = newsAdapter
            itemAnimator = null
        }

        newsViewModel.breakingNews.observe(viewLifecycleOwner){
            newsAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        newsAdapter.addLoadStateListener { loadState ->
            binding.apply{
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
            }
        }
    }

}