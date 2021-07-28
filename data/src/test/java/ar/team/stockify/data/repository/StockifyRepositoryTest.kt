package ar.team.stockify.data.repository

import ar.team.stockify.data.source.LocalDataSource
import ar.team.stockify.data.source.RemoteDataSource
import ar.team.stockify.domain.User
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class StockifyRepositoryTest {

    @Mock
    lateinit var localDataSource: LocalDataSource

    @Mock
    lateinit var remoteDataSource: RemoteDataSource

    @Mock
    lateinit var stockifyRepository: StockifyRepository

    private val apiKey = "5ZH64HR26H20SJ7T"

    @Before
    fun setUp() {
        stockifyRepository =
            StockifyRepository(localDataSource, remoteDataSource, apiKey)
    }

    @Test
    fun `getUser calls local data source`() {
        runBlocking {
            val user = mockedUser.copy()
            whenever(localDataSource.getUser()).thenReturn(user)

            val result = stockifyRepository.getUser()

            assertEquals(user, result)
        }
    }

    @Test
    fun `setUser calls local data source`() {
        runBlocking {
            val user = mockedUser.copy()
            whenever(localDataSource.setUser("name", "avatar")).thenReturn(user)

            val result = stockifyRepository.setUser("name", "avatar")

            assertEquals(user, result)
        }
    }

    private val mockedUser = User("Ch", "prueba.jpg")
}
