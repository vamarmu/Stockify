package ar.team.stockify.usecases

import ar.team.stockify.data.repository.StockifyRepository
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class AddRemoveFavUseCaseTest {


    @Mock
    lateinit var stockifyRepository: StockifyRepository

    lateinit var addRemoveFavUseCase: AddRemoveFavUseCase



    @Before
    fun setUp(){
        addRemoveFavUseCase = AddRemoveFavUseCase(stockifyRepository)
    }


    @Test
    fun addFavourite(){


    }
}