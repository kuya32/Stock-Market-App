package com.github.kuya32.stockmarketapp.data.mapper

import com.github.kuya32.stockmarketapp.data.local.IntradayInfoEntity
import com.github.kuya32.stockmarketapp.data.remote.dto.IntradayInfoDto
import com.github.kuya32.stockmarketapp.domain.model.IntradayInfo
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

fun IntradayInfoDto.toIntradayInfo(): IntradayInfo {
    val pattern = "yyyy-MM-dd HH:mm:ss"
    val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
    val localDateTime = LocalDateTime.parse(timestamp, formatter)
    return IntradayInfo(
        date = localDateTime,
        close = close,
        symbol = symbol
    )
}

fun IntradayInfoEntity.toIntradayInfo(): IntradayInfo {
    val pattern = "yyyy-MM-dd HH:mm:ss"
    val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
    val localDateTime = LocalDateTime.parse(date, formatter)
    return IntradayInfo(
        date = localDateTime,
        close = close.toDouble(),
        symbol = symbol
    )
}

fun IntradayInfo.toIntradayInfoEntity(): IntradayInfoEntity {
    return IntradayInfoEntity(
        date = date.toString(),
        close = close.toString(),
        symbol = symbol
    )
}