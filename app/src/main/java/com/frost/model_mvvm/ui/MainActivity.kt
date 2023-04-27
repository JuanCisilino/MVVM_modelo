package com.frost.model_mvvm.ui

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.RemoteViews
import android.widget.TextView
import androidx.activity.viewModels
import com.frost.model_mvvm.R
import com.frost.model_mvvm.databinding.ActivityMainBinding
import com.frost.model_mvvm.model.LocalCurrency
import com.frost.model_mvvm.utils.*
import com.frost.model_mvvm.widget.CurrencyWidget
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.onCreate(this)
        setListeners()
        subscribeToLiveData()
    }

    private fun setListeners() {
        binding.customView.onClickCallback = { loadSelectedCurrency(it) }
        setSearchField()
        binding.button.setOnClickListener { calculate() }
    }

    private fun setSearchField(){
        binding.editText.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (!hasFocus) { checkIfFocus(view) }
        }
        binding.editText.setOnEditorActionListener { view, actionId, _ -> onActionDone(actionId, view) }
    }

    private fun onActionDone(actionId: Int, view: TextView) =
        if (actionId == EditorInfo.IME_ACTION_DONE){
            checkIfFocus(view)
            true
        } else {
            false
        }

    private fun checkIfFocus(view: View) {
        if (!binding.editText.text.isNullOrEmpty()) {
            calculate()
            hide(view)
        }
    }

    private fun calculate() {
        val amount = binding.editText.text.toString().toDouble()
        val price = binding.selectedTextView.text.toString().toDouble()
        binding.resultTextView.text = (amount * price).toString()
    }

    private fun subscribeToLiveData() {
        viewModel.currencyLiveData.observe(this) { loadList(it) }
        viewModel.loadStateLiveData.observe(this) { handleLoadingState(it) }
    }

    private fun handleLoadingState(state: LoadState) {
        when (state) {
            LoadState.Loading -> { binding.customView.showLoading() }
            LoadState.Success -> { binding.customView.showItems() }
            else -> {}
        }
    }

    private fun loadList(currencyList: List<LocalCurrency>?) {
        currencyList
            ?.let { updateWidgetAndSetData(it) }
            ?:run { showToast(this, "Error al cargar la lista") }
    }

    private fun updateWidgetAndSetData(localCurrencyList: List<LocalCurrency>) {
        updateWidget()
        binding.customView.updateItems(localCurrencyList)
    }

    private fun updateWidget(){
        val appWidgetManager = AppWidgetManager.getInstance(this)
        val widget = ComponentName(this, CurrencyWidget::class.java)
        val views = RemoteViews(this.packageName, R.layout.currency_widget)
        setDataOnWidget(views)
        appWidgetManager.updateAppWidget(widget, views)
    }

    private fun setDataOnWidget(views: RemoteViews){
        val blue = getPref()?.getString("Dolar Blue", "0.0")
        val oficial = getPref()?.getString("Dolar Oficial", "0.0")
        val minorista = getPref()?.getString("Dolar turista", "0.0")
        // Construct the RemoteViews object
        views.setTextViewText(R.id.appwidget_blue, "Blue\n$$blue")
        views.setTextViewText(R.id.appwidget_oficial, "Oficial\n$$oficial")
        views.setTextViewText(R.id.appwidget_minorista, "Turista\n$$minorista")
    }

    private fun loadSelectedCurrency(value: Double) {
        binding.selectedTextView.text = value.toString()
    }
}