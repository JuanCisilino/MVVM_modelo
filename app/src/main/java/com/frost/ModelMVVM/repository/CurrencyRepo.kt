package com.frost.ModelMVVM.repository

import retrofit2.http.GET

interface CurrencyRepo {

    @GET
    suspend fun getOficialUsd()
}