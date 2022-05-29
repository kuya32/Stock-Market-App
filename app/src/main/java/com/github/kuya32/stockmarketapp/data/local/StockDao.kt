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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIntradayInfoListings(
        symbol: String,
        intradayListingEntities: List<IntradayInfoEntity>
    ) {
//        println("HELLLO")
//        for (i in intradayListingEntities) {
//            i.symbol = symbol
//        }
    }

    @Query("DELETE FROM intradayinfoentity WHERE symbol == :symbol")
    suspend fun deleteIntraInfoInstance(symbol: String)
}