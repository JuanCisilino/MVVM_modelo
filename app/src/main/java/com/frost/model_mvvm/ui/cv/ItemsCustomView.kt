package com.frost.model_mvvm.ui.cv

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.frost.model_mvvm.databinding.CustomViewItemsBinding
import com.frost.model_mvvm.model.LocalCurrency
import java.text.NumberFormat
import java.util.*

class ItemsCustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): ConstraintLayout(context, attrs, defStyleAttr) {

    private var binding: CustomViewItemsBinding =
        CustomViewItemsBinding.inflate(LayoutInflater.from(context), this, true)

    var onClickCallback: ((value: Double) -> Unit)?= null

    init {
        with(binding){
            blueLayout.setOnClickListener { onClickCallback?.invoke(toDouble(bluePrice.text.toString())) }
            oficialLayout.setOnClickListener { onClickCallback?.invoke(toDouble(oficialPrice.text.toString())) }
            minoristaLayout.setOnClickListener { onClickCallback?.invoke(toDouble(minoristaPrice.text.toString())) }
        }
    }

    private fun toDouble(value: String): Double {
        val format = NumberFormat.getInstance(Locale.getDefault())
        val number = format.parse(value)
        return number?.toDouble()?:0.0
    }

    fun updateItems(list: List<LocalCurrency>) {
        val localBlue = list.find { it.name?.contains("Blue") == true }
        val localOficial = list.find { it.name?.contains("Oficial") == true }
        val localMinorista = list.find { it.name?.contains("Turista") == true}
        with(binding){
            blue.text = localBlue?.name
            bluePrice.text = localBlue?.v.toString()

            oficial.text = localOficial?.name
            oficialPrice.text = localOficial?.v.toString()

            minorista.text = localMinorista?.name
            minoristaPrice.text = localMinorista?.v.toString()
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