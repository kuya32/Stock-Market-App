package com.github.kuya32.stockmarketapp.data.remote.dto

data class IntradayInfoDto(
    val timestamp: String,
    val close: Double,
    val symbol: String = ""
)
