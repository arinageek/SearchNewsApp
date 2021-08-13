package com.example.searchnewsapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import com.example.searchnewsapp.R
import com.example.searchnewsapp.adapters.NewsAdapter
import com.example.searchnewsapp.databinding.SearchNewsFragmentBinding
import com.example.searchnewsapp.ui.NewsViewModel
import com.example.searchnewsapp.util.Constants.Companion.SEARCH_NEWS_DELAY
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.search_news_fragment.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchNewsFragment : Fragment(R.layout.search_news_fragment) {

    private val newsViewModel by viewModels<NewsViewModel>()
    private lateinit var binding: SearchNewsFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = SearchNewsFragmentBinding.bind(view)

        var job : Job? = null
        binding.etSearch.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch{
                delay(SEARCH_NEWS_DELAY)
                editable?.let{
                    if(editable.toString().isNotEmpty()) newsViewModel.searchNews(editable.toString())
                }
            }
        }

        val newsAdapter = NewsAdapter()
        binding.rvSearchNews.apply{
            setHasFixedSize(true)
            adapter = newsAdapter
            itemAnimator = null
        }

        newsViewModel.searchNews.observe(viewLifecycleOwner){
            newsAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        newsAdapter.addLoadStateListener { loadState ->
            binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
        }
    }

}