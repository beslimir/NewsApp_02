package com.example.newsapp_02.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.newsapp_02.R
import com.example.newsapp_02.databinding.ActivityMainBinding
import com.example.newsapp_02.presentation.adapter.NewsAdapter
import com.example.newsapp_02.presentation.view_model.NewsViewModel
import com.example.newsapp_02.presentation.view_model.NewsViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    @Inject lateinit var factory: NewsViewModelFactory
    @Inject lateinit var newsAdapter: NewsAdapter
    lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        binding.bottomNavigationMenu.setupWithNavController(binding.navHostFragment.findNavController())
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.bottomNavigationMenu, navController)

        viewModel = ViewModelProvider(this, factory).get(NewsViewModel::class.java)
    }
}