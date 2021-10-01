package com.frost.ModelMVVM

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.frost.ModelMVVM.model.LocalCurrency
import com.frost.ModelMVVM.repository.CurrencyRepo
import com.frost.ModelMVVM.repository.InstanceRepo
import rx.schedulers.Schedulers
import java.util.ArrayList

class MainViewModel: ViewModel() {

    val currencyLiveData = MutableLiveData<List<LocalCurrency>?>()
    private val instance = InstanceRepo.getRetofitInstance().create(CurrencyRepo::class.java)
    private var finalCurrencyList = ArrayList<LocalCurrency>()

    suspend fun makeApiCall() {
        getOficial()
        getBlue()
        getMinorista()
    }

    private suspend fun getOficial(){
        instance.getOficialUsd()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe(
                {getPositiveAnswer(it, "Dolar Oficial")},
                {getNegativeAnswer()})
    }

    private suspend fun getBlue(){
        instance.getBlueUsd()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe(
                {getPositiveAnswer(it, "Dolar Blue")},
                {getNegativeAnswer()})
    }

    private suspend fun getMinorista(){
        instance.getOficialUsd()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe(
                {getPositiveAnswer(it, "Dolar Minorista")},
                {getNegativeAnswer()})
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
