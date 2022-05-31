package com.github.kuya32.stockmarketapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StockDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompanyListings(
        companyListingEntities: List<CompanyListingEntity>
    )

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIntradayInfoListings(
        intradayListingEntities: List<IntradayInfoEntity>
    )

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompanyInfo(
        companyInfoEntities: CompanyInfoEntity
    )

    @Query("DELETE FROM companylistingentity")
    suspend fun clearCompanyListings()

    @Query(
        """
            SELECT * FROM companylistingentity
            WHERE LOWER(name) LIKE '%' || LOWER(:query) || '%' OR
                UPPER(:query) == symbol
        """
    )
    suspend fun searchCompanyListing(query: String): List<CompanyListingEntity>

    @Query("SELECT * FROM intradayinfoentity WHERE symbol == :symbol")
    suspend fun searchCompanyIntradayInfoListing(symbol: String): List<IntradayInfoEntity>

    @Query("SELECT * FROM companyinfoentity WHERE symbol == :symbol")
    suspend fun searchCompanyInfo(symbol: String): CompanyInfoEntity

    @Query("DELETE FROM intradayinfoentity WHERE symbol == :symbol")
    suspend fun deleteIntradayInfoInstance(symbol: String)

    @Query("DELETE FROM companyinfoentity WHERE symbol == :symbol")
    suspend fun deleteCompanyInfoInstance(symbol: String)
}