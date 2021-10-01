package com.frost.ModelMVVM.repository

import com.frost.ModelMVVM.model.LocalCurrency
import retrofit2.http.GET
import rx.Observable


interface CurrencyRepo {

    @GET("usd")
    fun getBlueUsd(): Observable<List<LocalCurrency>>

    @GET("usd_of")
    fun getOficialUsd(): Observable<List<LocalCurrency>>

    @GET("usd_of_minorista")
    fun getUsdMinorista(): Observable<List<LocalCurrency>>
}