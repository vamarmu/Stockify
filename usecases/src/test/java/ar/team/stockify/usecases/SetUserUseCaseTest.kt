package ar.team.stockify.usecases

import ar.team.stockify.data.repository.StockifyRepository
import ar.team.stockify.domain.User
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class SetUserUseCaseTest {

    @Mock
    lateinit var stockifyRepository: StockifyRepository

    lateinit var setUserUseCase: SetUserUseCase

    @Before
    fun setUp() {
        setUserUseCase = SetUserUseCase(stockifyRepository)
    }

    @Test
    fun `invoke calls stockify repository`() {
        runBlocking {

            val user = mockedUser.copy()
            whenever(stockifyRepository.setUser("Ch", "prueba.jpg")).thenReturn(user)

            val result = setUserUseCase.invoke("Ch", "prueba.jpg")

            Assert.assertEquals(user, result)
        }
    }

    private val mockedUser = User("Ch", "prueba.jpg")
}