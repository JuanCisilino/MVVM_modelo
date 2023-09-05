package com.frost.model_mvvm.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.frost.model_mvvm.model.Casa
import com.frost.model_mvvm.uc.CurrencyUseCase
import com.frost.model_mvvm.utils.LoadState
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest{
    @RelaxedMockK
    private lateinit var currencyUseCase: CurrencyUseCase

    private lateinit var viewModel: MainViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        viewModel = MainViewModel(currencyUseCase)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter(){
        Dispatchers.resetMain()
    }

    @Test
    fun `when viewmodel is created at the first time, get all items and set it, then validate live data is not null`() = runTest {

        //Given
        coEvery { currencyUseCase.getValoresPrincipales() } returns getMockedCasaList()

        //When
        viewModel.onCreate()


        //Then
        assert(viewModel.currencyLiveData.value != null)
    }

    @Test
    fun `when search returns null validate live data value as null`() = runTest {
        //Given
        coEvery { currencyUseCase.getValoresPrincipales() } returns null

        //When
        viewModel.onCreate()

        //Then
        assert(viewModel.currencyLiveData.value == null)
    }

    @Test
    fun `when search returns null validate live data state value as null`() = runTest {
        //Given
        coEvery { currencyUseCase.getValoresPrincipales() } returns null

        //When
        viewModel.onCreate()

        //Then
        assert(viewModel.loadStateLiveData.value == LoadState.Error)
    }

    private fun getMockedCasaList(): List<Casa>{
        return listOf(
            Casa(
                nombre= "Oficial",
                venta= 34.56),
            Casa(
                nombre= "Blue",
                venta= 34.56),
            Casa(
                nombre= "turista",
                venta= 34.56)
        )
    }

}