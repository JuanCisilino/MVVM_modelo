package com.frost.model_mvvm.repository

import com.frost.model_mvvm.model.ValoresPrincipales
import com.frost.model_mvvm.service.CurrencyApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CurrencyRepository@Inject constructor(private val api: CurrencyApi) {

    suspend fun getValores(): List<ValoresPrincipales>{
        return withContext(Dispatchers.IO){
            val response = api.getTodos()
            response.body() ?: emptyList()
        }
    }
}