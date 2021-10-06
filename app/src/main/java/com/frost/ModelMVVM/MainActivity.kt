package com.frost.ModelMVVM

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.frost.ModelMVVM.adapter.CurrencyAdapter
import com.frost.ModelMVVM.databinding.ActivityMainBinding
import com.frost.ModelMVVM.model.LocalCurrency

class MainActivity : AppCompatActivity() {

    private val viewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }
    private lateinit var currAdapter: CurrencyAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpRecycler()
        viewModel.makeApiCall()
        setListeners()
        subscribeToLiveData()
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
        viewModel.currencyLiveData.observe(this, { loadList(it) })
    }

    private fun loadList(currencyList: List<LocalCurrency>?) {
        currencyList
            ?.let { currAdapter.setList(it) }
            ?:run { Toast.makeText(this, "Error al cargar la lista", Toast.LENGTH_SHORT).show() }
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