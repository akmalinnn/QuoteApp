package com.akmalin.quoteapp.presentation.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.akmalin.quoteapp.data.datasource.QuoteApiDataSource
import com.akmalin.quoteapp.data.datasource.QuoteDataSource
import com.akmalin.quoteapp.data.repository.QuoteRepository
import com.akmalin.quoteapp.data.repository.QuoteRepositoryImpl
import com.akmalin.quoteapp.data.source.network.services.QuoteApiServices
import com.akmalin.quoteapp.databinding.ActivityMainBinding
import com.akmalin.quoteapp.presentation.main.adapter.QuoteAdapter
import com.akmalin.quoteapp.utils.GenericViewModelFactory
import com.akmalin.quoteapp.utils.proceedWhen

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel: MainViewModel by viewModels {
        val s = QuoteApiServices.invoke()
        val ds: QuoteDataSource = QuoteApiDataSource(s)
        val rp: QuoteRepository = QuoteRepositoryImpl(ds)
        GenericViewModelFactory.create(MainViewModel(rp))
    }

    private lateinit var quoteAdapter: QuoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupRecyclerView()
        getAllQuote()
        observeData()
    }

    private fun setupRecyclerView() {
        quoteAdapter = QuoteAdapter()
        binding.rvItem.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 1) // Adjust the number of columns as needed
            adapter = quoteAdapter
        }
    }

    private fun getAllQuote() {
        viewModel.fetchQuotes()
    }

    private fun observeData() {
        viewModel.quoteLiveData.observe(this) { response ->
            response.proceedWhen(
                doOnSuccess = { result ->
                    val quotes = result.payload ?: emptyList()
                    quoteAdapter.submitList(quotes)
                },
                doOnLoading = {
                    binding.tvMain.text = "Loading..."
                },
                doOnError = { error ->
                    binding.tvMain.text = "Error : ${error.exception?.message}"
                }
            )
        }
    }

}
