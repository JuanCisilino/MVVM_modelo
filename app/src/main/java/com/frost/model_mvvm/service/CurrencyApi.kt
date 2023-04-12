package com.frost.model_mvvm.service

import com.frost.model_mvvm.model.LocalCurrency
import retrofit2.Response
import retrofit2.http.GET

interface CurrencyApi {

    @GET("usd")
    suspend fun getBlue(): Response<List<LocalCurrency>>

    @GET("usd_of")
    suspend fun getOficial(): Response<List<LocalCurrency>>

    @GET("usd_of_minorista")
    suspend fun getMinorista(): Response<List<LocalCurrency>>
}