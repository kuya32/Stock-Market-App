package com.github.kuya32.stockmarketapp.di

import com.github.kuya32.stockmarketapp.data.csv.CSVParser
import com.github.kuya32.stockmarketapp.data.csv.CompanyListingParser
import com.github.kuya32.stockmarketapp.data.repository.StockRepositoryImpl
import com.github.kuya32.stockmarketapp.domain.model.CompanyListing
import com.github.kuya32.stockmarketapp.domain.repository.StockRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCompanyListingsParser(
        companyListingParser: CompanyListingParser
    ): CSVParser<CompanyListing>

    @Binds
    @Singleton
    abstract fun bindStockRepository(
        stockRepositoryImpl: StockRepositoryImpl
    ): StockRepository
}