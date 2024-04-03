package com.akmalin.quoteapp.data.datasource

import com.akmalin.quoteapp.data.source.network.model.QuoteResponse
import com.akmalin.quoteapp.data.source.network.services.QuoteApiServices

interface QuoteDataSource {
    suspend fun getRandomQuotes(): List<QuoteResponse>
}

class QuoteApiDataSource(private val service: QuoteApiServices) : QuoteDataSource {
    override suspend fun getRandomQuotes(): List<QuoteResponse> {
        return service.getRandomQuotes().take(10)
    }
}