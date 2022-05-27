package com.github.kuya32.stockmarketapp.data.remote.dto

import java.sql.Timestamp

data class IntradayInfoDto(
    val timestamp: String,
    val close: Double
)
