package com.example.newsapp_02

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

/**
 *
 * Use cases:
 * - Get news headlines
 * - Save news
 * - Get saved news
 * - Delete saved news
 * - Search news
 * **/

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}