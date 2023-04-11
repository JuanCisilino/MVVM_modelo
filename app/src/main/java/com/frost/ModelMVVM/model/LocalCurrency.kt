package com.frost.ModelMVVM.model

import androidx.recyclerview.widget.DiffUtil
import java.io.Serializable


data class LocalCurrency(var v: Double?, var name: String?): Serializable, DiffUtil.ItemCallback<LocalCurrency>() {
    override fun areItemsTheSame(oldItem: LocalCurrency, newItem: LocalCurrency): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: LocalCurrency, newItem: LocalCurrency): Boolean {
        return oldItem == newItem
    }
}