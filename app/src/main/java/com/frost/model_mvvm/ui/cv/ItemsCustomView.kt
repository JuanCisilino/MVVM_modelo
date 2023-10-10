package com.frost.model_mvvm.ui.cv

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.frost.model_mvvm.databinding.CustomViewItemsBinding
import com.frost.model_mvvm.model.LocalCurrency

class ItemsCustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): ConstraintLayout(context, attrs, defStyleAttr) {

    private var binding: CustomViewItemsBinding =
        CustomViewItemsBinding.inflate(LayoutInflater.from(context), this, true)

    private lateinit var localBlue : LocalCurrency
    private lateinit var localOficial : LocalCurrency
    private lateinit var localMinorista : LocalCurrency

    var onClickCallback: ((value: Double) -> Unit)?= null

    init {
        with(binding){
            blueLayout.setOnClickListener { onClickCallback?.invoke(localBlue.v?:0.0) }
            oficialLayout.setOnClickListener { onClickCallback?.invoke(localOficial.v?:0.0) }
            minoristaLayout.setOnClickListener { onClickCallback?.invoke(localMinorista.v?:0.0) }
        }
    }

    fun updateItems(list: List<LocalCurrency>) {
        localBlue = list.find { it.name?.contains("Blue") == true }?: LocalCurrency(0.0, "")
        localOficial = list.find { it.name?.contains("Oficial") == true }?: LocalCurrency(0.0, "")
        localMinorista = list.find { it.name?.contains("Turista") == true}?: LocalCurrency(0.0, "")
        bindValues()
    }

    private fun bindValues() {
        with(binding){
            blue.text = localBlue.name
            bluePrice.text = localBlue.v.toString()

            oficial.text = localOficial.name
            oficialPrice.text = localOficial.v.toString()

            minorista.text = localMinorista.name
            minoristaPrice.text = localMinorista.v.toString()
        }
    }

    fun showItems(){
        binding.layoutDefault.visibility = View.VISIBLE
        binding.shimmerViewContainer.visibility = View.GONE
    }

    fun showLoading(){
        binding.layoutDefault.visibility = View.GONE
        binding.shimmerViewContainer.visibility = View.VISIBLE
    }
}