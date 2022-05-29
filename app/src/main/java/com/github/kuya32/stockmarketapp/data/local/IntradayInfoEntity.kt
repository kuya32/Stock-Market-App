package com.github.kuya32.stockmarketapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity
data class IntradayInfoEntity(
    val date: String,
    val close: Double,
    var symbol: String,
    @PrimaryKey val id: Int? = null
)