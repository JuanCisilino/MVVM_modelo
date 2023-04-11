package com.frost.ModelMVVM.repository

import com.frost.ModelMVVM.model.LocalCurrency
import com.frost.ModelMVVM.service.CurrencyApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CurrencyRepository@Inject constructor(private val api: CurrencyApi) {

    suspend fun getBlue(): List<LocalCurrency> {
        return withContext(Dispatchers.IO) {
            val response = api.getBlue()
            response.body() ?: emptyList()
        }
    }

    suspend fun getOficial(): List<LocalCurrency> {
        return withContext(Dispatchers.IO) {
            val response = api.getOficial()
            response.body() ?: emptyList()
        }
    }

    suspend fun getMinorista(): List<LocalCurrency> {
        return withContext(Dispatchers.IO) {
            val response = api.getMinorista()
            response.body() ?: emptyList()
        }
    }
}