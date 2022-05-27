package com.github.kuya32.stockmarketapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity
data class IntradayInfoEntity(
    val date: LocalDateTime,
    val close: Double,
    @PrimaryKey val id: Int? = null
)