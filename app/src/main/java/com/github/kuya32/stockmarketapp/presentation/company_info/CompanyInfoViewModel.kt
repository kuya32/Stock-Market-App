package com.github.kuya32.stockmarketapp.presentation.company_info

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.kuya32.stockmarketapp.domain.repository.StockRepository
import com.github.kuya32.stockmarketapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyInfoViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: StockRepository
): ViewModel() {

    private lateinit var symbol: String
    var state by mutableStateOf(CompanyInfoState())

    init {
        getCompanyInfoRemote()
    }

    fun onEvent(event: CompanyInfoEvent) {
        when (event) {
            is CompanyInfoEvent.Refresh -> {
                getCompanyInfoRemote(fetchFromRemote = true)
            }
        }
    }

    private fun getCompanyInfoRemote(fetchFromRemote: Boolean = false) {
        viewModelScope.launch {
            symbol = savedStateHandle.get<String>("symbol") ?: return@launch
            state = state.copy(isLoading = true)
            val companyInfoResult = async { repository.getCompanyInfo(fetchFromRemote, symbol) }
            val intradayInfoResult = async { repository.getIntradayInfo(fetchFromRemote, symbol) }
            when(val result = companyInfoResult.await()) {
                is Resource.Success -> {
                    state = state.copy(
                        company = result.data,
                        isLoading = false,
                        error = null
                    )
                }
                is Resource.Error -> {
                    state = state.copy(
                        isLoading = false,
                        error = result.message,
                        company = null
                    )
                }
                else -> Unit
            }
            when(val result = intradayInfoResult.await()) {
                is Resource.Success -> {
                    state = state.copy(
                        stockInfos = result.data ?: emptyList(),
                        isLoading = false,
                        error = null
                    )
                }
                is Resource.Error -> {
                    state = state.copy(
                        isLoading = false,
                        error = result.message,
                        company = null
                    )
                }
                else -> Unit
            }
        }
    }
}