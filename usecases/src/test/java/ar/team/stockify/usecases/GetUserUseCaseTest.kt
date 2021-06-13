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
class GetUserUseCaseTest {

    @Mock
    lateinit var stockifyRepository: StockifyRepository

    lateinit var getUserUseCase: GetUserUseCase

    @Before
    fun setUp() {
        getUserUseCase = GetUserUseCase(stockifyRepository)
    }

    @Test
    fun `invoke calls stockify repository`() {
        runBlocking {

            val user = mockedUser.copy()
            whenever(stockifyRepository.getUser()).thenReturn(user)

            val result = getUserUseCase.invoke()

            Assert.assertEquals(user, result)
        }
    }

    private val mockedUser = User("Ch", "prueba.jpg")
}