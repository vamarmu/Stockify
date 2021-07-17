package ar.team.stockify.usecases

import ar.team.stockify.data.repository.StockifyRepository
import ar.team.stockify.testshared.mockedListStockDetail
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class GetStockDetailUseCaseTest {

    @Mock
    lateinit var stockifyRepository: StockifyRepository

    lateinit var getStockDetailUseCase : GetStockDetailUseCase

    @Before
    fun setUp(){
        getStockDetailUseCase = GetStockDetailUseCase(stockifyRepository)
    }

    @After
    fun tearDown(){

    }


    @Test
    fun getStockDetail(){
        runBlocking {
            val mockDetails = mockedListStockDetail.take(2)
            whenever(stockifyRepository.getStockDetail("Stock")).thenReturn(mockDetails)

            val details = getStockDetailUseCase.invoke("Stock")

            assertNotNull(details)
            assertEquals(mockDetails.size, details!!.size)
        }

    }

}