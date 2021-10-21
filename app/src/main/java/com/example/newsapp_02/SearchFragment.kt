package com.example.newsapp_02

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp_02.Constants.Companion.COUNTRY
import com.example.newsapp_02.Constants.Companion.DELAY_TIME
import com.example.newsapp_02.data.util.Resource
import com.example.newsapp_02.databinding.FragmentSearchBinding
import com.example.newsapp_02.presentation.adapter.NewsAdapter
import com.example.newsapp_02.presentation.view_model.NewsViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {

    private lateinit var newsViewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var fragmentSearchBinding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentSearchBinding = FragmentSearchBinding.bind(view)
        newsViewModel = (activity as MainActivity).viewModel
        newsAdapter = (activity as MainActivity).newsAdapter

        initRecyclerView()
        getSearchNewsList()

        newsAdapter.setOnItemClickListener { article ->
            val bundle = Bundle().apply {
                putSerializable("article", article)
            }
            findNavController().navigate(
                R.id.action_searchFragment_to_infoFragment,
                bundle
            )
        }
    }

    private fun initRecyclerView() {
        fragmentSearchBinding.rvSearchNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun getSearchNewsList() {
        var job: Job? = null
        fragmentSearchBinding.etSearch.addTextChangedListener {
            job?.cancel()
            job = MainScope().launch {
                delay(DELAY_TIME)
                fragmentSearchBinding.etSearch.let {
                    if (fragmentSearchBinding.etSearch.text.toString().isNotEmpty()) {
                        newsViewModel.getSearchNews(COUNTRY, fragmentSearchBinding.etSearch.text.toString())
                    } else {
                        //TODO: here we can implement to show some initial list of news (maybe from news fragment...)
                    }
                }
            }
        }

        newsViewModel.searchNews.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    hideProgressBar()
                    it.data?.let { searchResponse ->
                        if (searchResponse.totalResults == 0) {
                            Log.d(Constants.LOG_TAG, "No results: $searchResponse")
                            Toast.makeText(activity, "Currently, there are no results available :(", Toast.LENGTH_LONG).show()
                        } else {
                            newsAdapter.differ.submitList(searchResponse.results)
                        }
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    it.message?.let { msg ->
                        Log.d(Constants.LOG_TAG, "Error: $msg")
                        Toast.makeText(activity, "An error occurred: $msg", Toast.LENGTH_SHORT).show()
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })
    }

    private fun hideProgressBar() {
        fragmentSearchBinding.pbSearch.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        fragmentSearchBinding.pbSearch.visibility = View.VISIBLE
    }

}











