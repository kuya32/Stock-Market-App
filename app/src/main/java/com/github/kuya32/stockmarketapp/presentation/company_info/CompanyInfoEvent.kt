package com.github.kuya32.stockmarketapp.presentation.company_info

sealed class CompanyInfoEvent {
    object Refresh: CompanyInfoEvent()
}
