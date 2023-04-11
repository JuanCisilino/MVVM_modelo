package com.frost.ModelMVVM.uc

import com.frost.ModelMVVM.model.LocalCurrency
import com.frost.ModelMVVM.repository.CurrencyRepository
import javax.inject.Inject

class CurrencyUseCase @Inject constructor(private val repo: CurrencyRepository) {

    suspend fun getBlue(): List<LocalCurrency>?{
        val list = repo.getBlue()
        return if (list.isNotEmpty()) list else null
    }

    suspend fun getOficial(): List<LocalCurrency>?{
        val list = repo.getOficial()
        return if (list.isNotEmpty()) list else null
    }

    suspend fun getMinorista(): List<LocalCurrency>?{
        val list = repo.getMinorista()
        return if (list.isNotEmpty()) list else null
    }

}