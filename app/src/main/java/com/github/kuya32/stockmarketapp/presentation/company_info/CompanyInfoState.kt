package com.github.kuya32.stockmarketapp.presentation.company_info

import com.github.kuya32.stockmarketapp.domain.model.CompanyInfo
import com.github.kuya32.stockmarketapp.domain.model.IntradayInfo

data class CompanyInfoState(
    val stockInfos: List<IntradayInfo> = emptyList(),
    val company: CompanyInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)