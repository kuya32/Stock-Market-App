package com.github.kuya32.stockmarketapp.data.remote

import androidx.room.Query
import okhttp3.ResponseBody
import retrofit2.http.GET

interface StockApi {

    @GET("query?function=LISTING_STATUS")
    suspend fun getListings(
        @Query("apikey") apiKey: String
    ): ResponseBody

    companion object {
        const val API_KEY = "F0A0FZOOZYW5YTM0"
        const val BASE_URL = "https://alphavantage.co"
    }
}