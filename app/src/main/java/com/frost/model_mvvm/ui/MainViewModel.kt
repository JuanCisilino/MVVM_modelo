package com.frost.model_mvvm.ui

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frost.model_mvvm.model.Casa
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
    var finalCurrencyList = ArrayList<LocalCurrency>()
    private lateinit var context: Context

    fun onCreate(contex: Context) {
        context = contex
        loadStateLiveData.postValue(LoadState.Loading)
        finalCurrencyList.clear()
        getTodos()
    }

    private fun getTodos() {
        viewModelScope.launch {
            val response = useCase.getValoresPrincipales()
            response
                ?.let { getNotNull(it) }
                ?:run { getNegativeAnswer() }
        }
    }

    private fun getNegativeAnswer(){
        loadStateLiveData.postValue(LoadState.Error)
        currencyLiveData.postValue(null)
    }

    private fun getNotNull(currencyList: List<Casa>){
        finalCurrencyList.clear()
        val oficial = currencyList.find { it.nombre.contains("Oficial") }
        val blue = currencyList.find { it.nombre.contains("Blue") }
        val turista = currencyList.find { it.nombre.contains("turista") }
        finalCurrencyList.add(LocalCurrency(oficial?.venta, oficial?.nombre))
        finalCurrencyList.add(LocalCurrency(blue?.venta, blue?.nombre))
        finalCurrencyList.add(LocalCurrency(turista?.venta, turista?.nombre))
        check()
    }

    private fun check() {
        if (finalCurrencyList.size == 3) {
            save()
            loadStateLiveData.postValue(LoadState.Success)
            currencyLiveData.postValue(finalCurrencyList)
        }
    }

    private fun save(){
        val prefs = context.getSharedPreferences("prefs_file", Context.MODE_PRIVATE)
        prefs.edit()?.clear()?.apply()
        val editor = prefs.edit()
        finalCurrencyList.forEach { editor.putString(it.name?:"", it.v?:"0.0") }
        editor.apply()
    }
}
