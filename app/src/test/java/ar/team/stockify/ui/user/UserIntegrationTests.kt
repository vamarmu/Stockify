package ar.team.stockify.ui.user

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import ar.team.stockify.FakeLocalDataSource
import ar.team.stockify.data.repository.StockifyRepository
import ar.team.stockify.data.source.LocalDataSource
import ar.team.stockify.data.source.RemoteDataSource
import ar.team.stockify.database.LocalDataSourceImp
import ar.team.stockify.database.StockDatabase
import ar.team.stockify.usecases.GetUserUseCase
import ar.team.stockify.usecases.SetUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class UserIntegrationTests {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var localDataSource: FakeLocalDataSource

    @Mock
    private lateinit var remoteDataSource: RemoteDataSource

    private lateinit var stockifyRepository: StockifyRepository

    private lateinit var setUserUseCase: SetUserUseCase

    private lateinit var getUserUseCase: GetUserUseCase

    private lateinit var vm: UserViewModel

    @Mock
    private lateinit var observer: Observer<UserViewModel.UiUserModel>

    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        localDataSource = FakeLocalDataSource()
        stockifyRepository = StockifyRepository(localDataSource, remoteDataSource, apiKey = "1234")
        setUserUseCase = SetUserUseCase(stockifyRepository)
        getUserUseCase = GetUserUseCase(stockifyRepository)
        vm = UserViewModel(getUserUseCase, setUserUseCase)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `when save user is called, GetUserUseCase returns user`() {
        runBlockingTest {
            vm.model.observeForever(observer)

            vm.saveUser("Ch", "prueba.jpg")

            val user = getUserUseCase.invoke()
            assertNotNull(user)
            assertEquals(user?.name, "Ch")
            assertEquals(user?.avatar, "prueba.jpg")
        }
    }

}