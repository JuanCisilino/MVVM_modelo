package com.frost.model_mvvm.uc

import com.frost.model_mvvm.model.Casa
import com.frost.model_mvvm.repository.CurrencyRepository
import javax.inject.Inject

class CurrencyUseCase @Inject constructor(private val repo: CurrencyRepository) {

    suspend fun getValoresPrincipales(): List<Casa>? {
        val listWithCasa = repo.getValores()
        return if (listWithCasa.isNotEmpty()) listWithCasa else null
    }
}