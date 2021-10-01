package com.frost.ModelMVVM.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.frost.ModelMVVM.R
import com.frost.ModelMVVM.model.LocalCurrency
import kotlinx.android.synthetic.main.item_currency.view.*

class CurrencyAdapter: RecyclerView.Adapter<CurrencyAdapter.CurrencyHolder>() {

    private var currencyList = ArrayList<LocalCurrency>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CurrencyHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.item_currency, parent, false)
        return CurrencyHolder(inflater)
    }

    override fun onBindViewHolder(holder: CurrencyHolder, position: Int) {
        holder.bind(currencyList[position])
    }

    override fun getItemCount() = currencyList.size

    inner class CurrencyHolder(private val view: View): RecyclerView.ViewHolder(view){

        fun bind(currency: LocalCurrency){
            view.nameTextView.text = currency.name
            view.releaseTextView.text = currency.v.toString()
        }

    }

    fun setList(list: List<LocalCurrency>){
        currencyList = list as ArrayList<LocalCurrency>
        this.notifyDataSetChanged()
    }
}