package com.frost.model_mvvm.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.frost.model_mvvm.R
import com.frost.model_mvvm.databinding.ItemCurrencyBinding
import com.frost.model_mvvm.model.LocalCurrency

class CurrencyAdapter: RecyclerView.Adapter<CurrencyAdapter.CurrencyHolder>() {

    private var currencyList = listOf<LocalCurrency>()
    var onClickCallback: ((value: Double) -> Unit)?= null

    fun updateItems(newItems: List<LocalCurrency>) {
        val diffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize() = currencyList.size

            override fun getNewListSize() = newItems.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                currencyList[oldItemPosition].name == newItems[newItemPosition].name

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                currencyList[oldItemPosition] == newItems[newItemPosition]

        })
        currencyList = newItems
        diffResult.dispatchUpdatesTo(this)
    }

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

        private val binding = ItemCurrencyBinding.bind(view)

        fun bind(currency: LocalCurrency){
            binding.nameTextView.text = currency.name
            binding.releaseTextView.text = currency.v.toString()
            view.setOnClickListener { currency.v?.let { it -> onClickCallback?.invoke(it) } }
        }

    }

}