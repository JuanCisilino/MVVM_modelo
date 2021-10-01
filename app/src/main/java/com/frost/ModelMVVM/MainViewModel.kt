package com.frost.ModelMVVM

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.frost.ModelMVVM.model.LocalCurrency

class MainViewModel: ViewModel() {

    private val currencyLiveData = MutableLiveData<LocalCurrency>()


}