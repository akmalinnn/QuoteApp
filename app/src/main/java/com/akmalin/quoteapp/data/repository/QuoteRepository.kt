package com.akmalin.quoteapp.data.repository

import com.akmalin.quoteapp.data.datasource.QuoteDataSource
import com.akmalin.quoteapp.data.mapper.toQuotes
import com.akmalin.quoteapp.data.model.Quote
import com.akmalin.quoteapp.data.source.network.model.QuoteResponse
import com.akmalin.quoteapp.data.source.network.services.QuoteApiServices
import com.akmalin.quoteapp.utils.ResultWrapper
import com.akmalin.quoteapp.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface QuoteRepository {
    fun getRandomQuotes() : Flow<ResultWrapper<List<Quote>>>
}

class  QuoteRepositoryImpl(private val dataSource: QuoteDataSource) : QuoteRepository {
    override fun getRandomQuotes(): Flow<ResultWrapper<List<Quote>>> {
        return proceedFlow { dataSource.getRandomQuotes().toQuotes() }
    }
}