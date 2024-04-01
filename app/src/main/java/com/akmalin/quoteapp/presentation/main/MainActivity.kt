package com.akmalin.quoteapp.presentation.main

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.akmalin.quoteapp.data.datasource.QuoteDataSource
import com.akmalin.quoteapp.data.repository.QuoteRepository
import com.akmalin.quoteapp.data.repository.QuoteRepositoryImpl
import com.akmalin.quoteapp.data.source.network.services.QuoteApiServices
import com.akmalin.quoteapp.databinding.ActivityMainBinding
import com.akmalin.quoteapp.utils.GenericViewModelFactory

class MainActivity : AppCompatActivity() {

    private val binding : ActivityMainBinding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel :MainViewModel by viewModels {
        val s = QuoteApiServices.invoke()
        val ds : QuoteDataSource = QuoteApiServices(s)
        val rp : QuoteRepository = QuoteRepositoryImpl(ds)
        GenericViewModelFactory.create(MainViewModel)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       setContentView(binding.root)
        }
    }
