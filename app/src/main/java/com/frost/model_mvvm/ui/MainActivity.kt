package com.frost.model_mvvm.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.frost.model_mvvm.ui.adapter.CurrencyAdapter
import com.frost.model_mvvm.databinding.ActivityMainBinding
import com.frost.model_mvvm.model.LocalCurrency
import com.frost.model_mvvm.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()
    private lateinit var currAdapter: CurrencyAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkAndCreate()
        setUpRecycler()
        setListeners()
        subscribeToLiveData()
    }

    private fun checkAndCreate() {
        if (isOtherDay()) {
            clearPrefs()
            saveDate()
        }
        viewModel.onCreate(isSharedEmpty(), getPref())
    }

    private fun setListeners() {
        currAdapter.onClickCallback = { loadSelectedCurrency(it) }
        binding.button.setOnClickListener { calculate() }
    }

    private fun calculate() {
        val amount = binding.editText.text.toString().toDouble()
        val price = binding.selectedTextView.text.toString().toDouble()
        binding.resultTextView.text = (amount * price).toString()
    }

    private fun subscribeToLiveData() {
        viewModel.currencyLiveData.observe(this) { loadList(it) }
    }

    private fun loadList(currencyList: List<LocalCurrency>?) {
        currencyList
            ?.let {
                checkAndSave(it)
                currAdapter.updateItems(it) }
            ?:run { showToast(this, "Error al cargar la lista") }
    }

    private fun checkAndSave(currencyList: List<LocalCurrency>){
        currencyList.forEach { savePrefs(it.name?:"", it.v?:0.0) }
    }

    private fun setUpRecycler() {
        currAdapter = CurrencyAdapter()
        binding.recyclerView.layoutManager = GridLayoutManager(this, 3)
        binding.recyclerView.adapter = currAdapter
    }

    private fun loadSelectedCurrency(value: Double) {
        binding.selectedTextView.text = value.toString()
    }
}