package com.github.kuya32.stockmarketapp.data.repository

import com.github.kuya32.stockmarketapp.data.csv.CSVParser
import com.github.kuya32.stockmarketapp.data.csv.IntradayInfoParser
import com.github.kuya32.stockmarketapp.data.local.IntradayInfoEntity
import com.github.kuya32.stockmarketapp.data.local.StockDatabase
import com.github.kuya32.stockmarketapp.data.mapper.*
import com.github.kuya32.stockmarketapp.data.remote.StockApi
import com.github.kuya32.stockmarketapp.domain.model.CompanyInfo
import com.github.kuya32.stockmarketapp.domain.model.CompanyListing
import com.github.kuya32.stockmarketapp.domain.model.IntradayInfo
import com.github.kuya32.stockmarketapp.domain.repository.StockRepository
import com.github.kuya32.stockmarketapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(
    private val api: StockApi,
    private val db: StockDatabase,
    private val companyListingParser: CSVParser<CompanyListing>,
    private val intradayInfoParser: CSVParser<IntradayInfo>
): StockRepository {

    private val dao = db.dao

    override suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>> {
        return flow {
            emit(Resource.Loading(true))
            val localListings = dao.searchCompanyListing(query)
            emit(Resource.Success(
                data = localListings.map { it.toCompanyListing() }
            ))

            val isDbEmpty = localListings.isEmpty() && query.isBlank()
            val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote
            if (shouldJustLoadFromCache) {
                emit(Resource.Loading(false))
                return@flow
            }
            val remoteListings = try {
                val response = api.getListings()
                companyListingParser.parse(response.byteStream())
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            }

            remoteListings?.let { listings ->
                dao.clearCompanyListings()
                dao.insertCompanyListings(
                    listings.map { it.toCompanyListingEntity() }
                )
                emit(Resource.Success(
                    data = dao.searchCompanyListing("")
                        .map { it.toCompanyListing() }
                ))
                emit(Resource.Loading(false))
            }
        }
    }

    override suspend fun getIntradayInfo(
        fetchFromRemote: Boolean,
        symbol: String
    ): Resource<List<IntradayInfo>> {

        val localListings = dao.searchCompanyIntradayInfoListing(symbol)

        val isListingsEmpty = localListings.isEmpty()
        val shouldJustLoadFromCache = !isListingsEmpty && !fetchFromRemote
        if (shouldJustLoadFromCache) {
            return Resource.Success(localListings.map { it.toIntradayInfo() })
        }

        val remoteIntradayListings = try {
            val response = api.getIntradayInfo(symbol)
            val results = intradayInfoParser.parse(response.byteStream())
            results.let { listings ->
                dao.deleteIntradayInfoInstance(symbol)
                dao.insertIntradayInfoListings(
                    intradayListingEntities = listings
                        .onEach { entity ->
                            entity.symbol = symbol
                        }
                        .map { it.toIntradayInfoEntity() }
                )
            }
            Resource.Success(results)
        } catch (e: IOException) {
            e.printStackTrace()
            Resource.Error(
                message = "Couldn't load intraday info"
            )
        } catch (e: HttpException) {
            e.printStackTrace()
            Resource.Error(
                message = "Couldn't load intraday info"
            )
        }

        return remoteIntradayListings
    }

    override suspend fun getCompanyInfo(
        fetchFromRemote: Boolean,
        symbol: String
    ): Resource<CompanyInfo> {

        val localCompanyInstance = dao.searchCompanyInfo(symbol)




        return try {
            val result = api.getCompanyInfo(symbol)
            dao.deleteCompanyInfoInstance(symbol)
            dao.insertCompanyInfo(result.toCompanyInfo().toCompanyInfoEntity())
            Resource.Success(result.toCompanyInfo())
        } catch (e: IOException) {
            e.printStackTrace()
            Resource.Error(
                message = "Couldn't load company info"
            )
        } catch (e: HttpException) {
            e.printStackTrace()
            Resource.Error(
                message = "Couldn't load company info"
            )
        }
    }

}