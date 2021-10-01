package com.frost.ModelMVVM

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.frost.ModelMVVM.adapter.CurrencyAdapter
import com.frost.ModelMVVM.model.LocalCurrency
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val viewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }
    private lateinit var currAdapter: CurrencyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpRecycler()
        viewModel.makeApiCall()
        subscribeToLiveData()
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
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        recyclerView.adapter = currAdapter
    }
}