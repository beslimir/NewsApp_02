package com.example.newsapp_02.presentation.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp_02.Constants.Companion.ARTICLES_PER_PAGE
import com.example.newsapp_02.Constants.Companion.COUNTRY
import com.example.newsapp_02.Constants.Companion.LOG_TAG
import com.example.newsapp_02.R
import com.example.newsapp_02.data.util.Resource
import com.example.newsapp_02.databinding.FragmentNewsBinding
import com.example.newsapp_02.presentation.adapter.NewsAdapter
import com.example.newsapp_02.presentation.view_model.NewsViewModel

class NewsFragment : Fragment() {

    private lateinit var viewModel: NewsViewModel
    private lateinit var fragmentNewsBinding: FragmentNewsBinding
    private lateinit var newsAdapter: NewsAdapter

    //pagination below
    private var page = 1
    private var isScrolling = false
    private var isLoading = false
    private var isLastPage = false
    private var pages = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    //called immediately after all views are created
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentNewsBinding = FragmentNewsBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel
        newsAdapter = (activity as MainActivity).newsAdapter

        initRecyclerView()
        viewNewsList()

        newsAdapter.setOnItemClickListener { article ->
            val bundle = Bundle().apply {
                putSerializable("article", article)
            }
            findNavController().navigate(
                R.id.action_newsFragment_to_infoFragment,
                bundle
            )
        }
    }

    private fun initRecyclerView() {
//        newsAdapter = NewsAdapter()
        fragmentNewsBinding.rvNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@NewsFragment.onScrollListener)
        }
    }

    private fun viewNewsList() {
        viewModel.getLatestNews(COUNTRY, page)
        viewModel.latestNews.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { apiResponse ->
                        if (apiResponse.totalResults == 0) {
                            Toast.makeText(activity,"Currently, there are no results available :(",Toast.LENGTH_LONG).show()
                        } else {
                            newsAdapter.differ.submitList(apiResponse.results)
                            //pagination
                            pages =
                                if (apiResponse.totalResults % ARTICLES_PER_PAGE == 0) apiResponse.totalResults / ARTICLES_PER_PAGE
                                else apiResponse.totalResults / ARTICLES_PER_PAGE + 1
                            isLastPage = page == pages
                        }
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let {
                        Toast.makeText(activity, "An error occurred: $it", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })
    }

    private val onScrollListener = object : RecyclerView.OnScrollListener() {

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                isScrolling = true
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = fragmentNewsBinding.rvNews.layoutManager as LinearLayoutManager
            val listSize = layoutManager.itemCount
            val visibleItems = layoutManager.childCount
            val firstVisiblePos = layoutManager.findFirstVisibleItemPosition()
            val hasReachedToTheEnd = firstVisiblePos + visibleItems >= listSize
            val shouldPaginate = !isLoading && !isLastPage && hasReachedToTheEnd && isScrolling

            Log.i(LOG_TAG, "onScrolled: \nList size: $listSize\n " +
                    "Visible items: $visibleItems\n " +
                    "First visible position: $firstVisiblePos\n " +
                    "Has reached to the end: $hasReachedToTheEnd\n " +
                    "Should paginate: $shouldPaginate\n")

            if(shouldPaginate) {
                page++
                viewModel.getLatestNews(COUNTRY, page)
                isScrolling = false
            }
        }
    }

    private fun showProgressBar() {
        isLoading = true
        fragmentNewsBinding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        isLoading = false
        fragmentNewsBinding.progressBar.visibility = View.INVISIBLE
    }
}