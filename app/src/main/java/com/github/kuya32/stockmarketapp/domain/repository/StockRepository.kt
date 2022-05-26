package com.github.kuya32.stockmarketapp.domain.repository

import com.github.kuya32.stockmarketapp.domain.model.CompanyListing
import com.github.kuya32.stockmarketapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {

    suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>>
}