package com.frost.model_mvvm.uc

import com.frost.model_mvvm.model.Casa
import com.frost.model_mvvm.repository.CurrencyRepository
import javax.inject.Inject

class CurrencyUseCase @Inject constructor(private val repo: CurrencyRepository) {

    suspend fun getValoresPrincipales(): List<Casa>? {
        val listWithCasa = repo.getValores()
        val listToReturn = listWithCasa.map { it.casa }
        return if (listToReturn.isNotEmpty()) listToReturn else null
    }
}