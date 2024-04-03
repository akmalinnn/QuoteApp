package com.akmalin.quoteapp.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akmalin.quoteapp.data.model.Quote
import com.akmalin.quoteapp.data.repository.QuoteRepository
import com.akmalin.quoteapp.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: QuoteRepository) : ViewModel() {
    private val _quoteLiveData = MutableLiveData<ResultWrapper<List<Quote>>>()
    val quoteLiveData: LiveData<ResultWrapper<List<Quote>>>
        get() = _quoteLiveData

    fun fetchQuotes() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getRandomQuotes().collect {
                _quoteLiveData.postValue(it)
            }
        }
    }
}
