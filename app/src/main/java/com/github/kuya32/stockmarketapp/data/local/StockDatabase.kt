package com.github.kuya32.stockmarketapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [CompanyListingEntity::class, IntradayInfoEntity::class, CompanyInfoEntity::class],
    version = 6,
    exportSchema = false
)
abstract class StockDatabase: RoomDatabase() {

    abstract val dao: StockDao
}