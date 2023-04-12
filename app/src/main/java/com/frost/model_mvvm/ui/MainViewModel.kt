package com.frost.model_mvvm.ui

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frost.model_mvvm.model.LocalCurrency
import com.frost.model_mvvm.uc.CurrencyUseCase
import com.frost.model_mvvm.utils.LoadState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.ArrayList
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val useCase: CurrencyUseCase): ViewModel() {

    val currencyLiveData = MutableLiveData<List<LocalCurrency>?>()
    var loadStateLiveData = MutableLiveData<LoadState>()
    private var finalCurrencyList = ArrayList<LocalCurrency>()

    fun onCreate(isOtherDay: Boolean, pref: SharedPreferences?=null) {
        loadStateLiveData.postValue(LoadState.Loading)
        if (isOtherDay){
            getOficial()
            getBlue()
            getMinorista()
        } else {
            createList(pref!!)
            check()
        }
    }

    private fun createList(pref: SharedPreferences) {
        finalCurrencyList.add(createLocalCurrency(pref, "blue"))
        finalCurrencyList.add(createLocalCurrency(pref, "oficial"))
        finalCurrencyList.add(createLocalCurrency(pref, "minorista"))
    }

    private fun createLocalCurrency(pref: SharedPreferences, name: String) = LocalCurrency(
        v = pref.getString(name, "0.0")?.toDouble(),
        name = name)

    private fun getOficial(){
        viewModelScope.launch {
            val result =  useCase.getOficial()

            result
                ?.let { getPositiveAnswer(it, "oficial") }
                ?:run { getNegativeAnswer() }
        }

    }

    private fun getBlue(){
        viewModelScope.launch {
            val result =  useCase.getBlue()

            result
                ?.let { getPositiveAnswer(it, "blue") }
                ?:run { getNegativeAnswer() }
        }
    }

    private fun getMinorista(){
        viewModelScope.launch {
            val result =  useCase.getMinorista()

            result
                ?.let { getPositiveAnswer(it, "minorista") }
                ?:run { getNegativeAnswer() }
        }
    }

    private fun getNegativeAnswer(){
        loadStateLiveData.postValue(LoadState.Error)
        currencyLiveData.postValue(null)
    }

    private fun getPositiveAnswer(currencyList: List<LocalCurrency>, name: String) {
        val lastFromList = currencyList.last()
        lastFromList.name = name
        finalCurrencyList.add(lastFromList)
        check()
    }

    private fun check() {
        if (finalCurrencyList.size == 3) {
            loadStateLiveData.postValue(LoadState.Success)
            currencyLiveData.postValue(finalCurrencyList)
        } else {
            loadStateLiveData.postValue(LoadState.Error)
        }
    }
}
