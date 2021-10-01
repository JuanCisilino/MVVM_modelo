package com.frost.ModelMVVM.repository

import com.frost.ModelMVVM.model.LocalCurrency
import retrofit2.http.GET
import rx.Observable


interface CurrencyRepo {

    @GET("usd")
    suspend fun getBlueUsd(): Observable<List<LocalCurrency>>

    @GET("usd_of")
    suspend fun getOficialUsd(): Observable<List<LocalCurrency>>

    @GET("usd_of_minorista")
    suspend fun getUsdMinorista(): Observable<List<LocalCurrency>>
}