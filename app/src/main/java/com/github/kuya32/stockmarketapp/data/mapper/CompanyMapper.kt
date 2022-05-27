package com.github.kuya32.stockmarketapp.data.mapper

import com.github.kuya32.stockmarketapp.data.local.CompanyListingEntity
import com.github.kuya32.stockmarketapp.data.remote.dto.CompanyInfoDto
import com.github.kuya32.stockmarketapp.domain.model.CompanyInfo
import com.github.kuya32.stockmarketapp.domain.model.CompanyListing

fun CompanyListingEntity.toCompanyListing(): CompanyListing {
    return CompanyListing(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}

fun CompanyListing.toCompanyListingEntity(): CompanyListingEntity {
    return CompanyListingEntity(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}

fun CompanyInfoDto.toCompanyInfo(): CompanyInfo {
    return CompanyInfo(
        symbol = symbol ?: "",
        description = description ?: "",
        name = name ?: "",
        country = country ?: "",
        industry = industry ?: ""
    )
}