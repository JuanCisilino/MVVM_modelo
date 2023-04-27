package com.frost.model_mvvm.service

import com.frost.model_mvvm.model.ValoresPrincipales
import retrofit2.Response
import retrofit2.http.GET

interface CurrencyApi {

    @GET("api.php?type=valoresprincipales")
    suspend fun getTodos(): Response<List<ValoresPrincipales>>
}