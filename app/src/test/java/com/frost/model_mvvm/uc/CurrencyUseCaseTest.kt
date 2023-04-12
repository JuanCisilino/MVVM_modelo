package com.frost.model_mvvm.uc

import com.frost.model_mvvm.model.LocalCurrency
import com.frost.model_mvvm.repository.CurrencyRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class CurrencyUseCaseTest{

    @RelaxedMockK
    private lateinit var repository: CurrencyRepository

    lateinit var currencyUseCase: CurrencyUseCase

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        currencyUseCase = CurrencyUseCase(repository)
    }

    @Test
    fun `when the api doesnt return anything then return null`() = runBlocking{
        //Given
        coEvery { currencyUseCase.getBlue() } returns emptyList()

        //When
        val response = currencyUseCase.getBlue()

        //Then
        assert(response == null)

    }

    @Test
    fun `when the api returns something then return empty list`() = runBlocking{
        val mockedList = listOf(LocalCurrency(name = "test", v = 0.0))
        //Given
        coEvery { currencyUseCase.getBlue() } returns mockedList

        //When
        val response = currencyUseCase.getBlue()

        //Then
        assert(response != null)

    }
}