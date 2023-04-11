package com.frost.ModelMVVM.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frost.ModelMVVM.model.LocalCurrency
import com.frost.ModelMVVM.uc.CurrencyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.ArrayList
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val useCase: CurrencyUseCase): ViewModel() {

    val currencyLiveData = MutableLiveData<List<LocalCurrency>?>()
    private var finalCurrencyList = ArrayList<LocalCurrency>()

    fun onCreate() {
        getOficial()
        getBlue()
        getMinorista()
    }

    private fun getOficial(){
        viewModelScope.launch {
            val result =  useCase.getOficial()

            result
                ?.let { getPositiveAnswer(it, "Dolar Oficial") }
                ?:run { getNegativeAnswer() }
        }

    }

    private fun getBlue(){
        viewModelScope.launch {
            val result =  useCase.getBlue()

            result
                ?.let { getPositiveAnswer(it, "Dolar Blue") }
                ?:run { getNegativeAnswer() }
        }
    }

    private fun getMinorista(){
        viewModelScope.launch {
            val result =  useCase.getMinorista()

            result
                ?.let { getPositiveAnswer(it, "Dolar Minorista") }
                ?:run { getNegativeAnswer() }
        }
    }

    private fun getNegativeAnswer(){
        currencyLiveData.postValue(null)
    }

    private fun getPositiveAnswer(currencyList: List<LocalCurrency>, name: String) {
        val lastFromList = currencyList.last()
        lastFromList.name = name
        finalCurrencyList.add(lastFromList)
        check()
    }

    private fun check() {
        if (finalCurrencyList.size == 3) currencyLiveData.postValue(finalCurrencyList)
    }
}
