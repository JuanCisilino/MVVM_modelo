package com.frost.ModelMVVM.service

import com.frost.ModelMVVM.model.LocalCurrency
import retrofit2.Response
import retrofit2.http.GET
import rx.Observable

interface CurrencyApi {

    @GET("usd")
    fun getBlueUsd(): Observable<List<LocalCurrency>>

    @GET("usd_of")
    fun getOficialUsd(): Observable<List<LocalCurrency>>

    @GET("usd_of_minorista")
    fun getUsdMinorista(): Observable<List<LocalCurrency>>

    @GET("usd")
    suspend fun getBlue(): Response<List<LocalCurrency>>

    @GET("usd_of")
    suspend fun getOficial(): Response<List<LocalCurrency>>

    @GET("usd_of_minorista")
    suspend fun getMinorista(): Response<List<LocalCurrency>>
}