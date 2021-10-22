package com.example.newsapp_02.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp_02.R
import com.example.newsapp_02.databinding.FragmentSavedBinding
import com.example.newsapp_02.presentation.adapter.NewsAdapter
import com.example.newsapp_02.presentation.view_model.NewsViewModel

class SavedFragment : Fragment() {

    private lateinit var newsViewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var fragmentSavedBinding: FragmentSavedBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_saved, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentSavedBinding = FragmentSavedBinding.bind(view)
        newsViewModel = (activity as MainActivity).viewModel
        newsAdapter = (activity as MainActivity).newsAdapter

        initRecyclerView()

        newsViewModel.getSavedNews().observe(viewLifecycleOwner, Observer {
            newsAdapter.differ.submitList(it)
        })

        newsAdapter.setOnItemClickListener { myArticle ->
            val bundle = Bundle().apply {
                putSerializable("article", myArticle)
            }
            findNavController().navigate(
                R.id.action_savedFragment_to_infoFragment,
                bundle
            )
        }

    }

    private fun initRecyclerView() {
        fragmentSavedBinding.rvNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}