package com.github.kuya32.stockmarketapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class IntradayInfoEntity(
    val date: String,
    val close: String,
    val symbol: String,
    @PrimaryKey val id: Int? = null
)