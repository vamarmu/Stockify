package ar.team.stockify.ui.user

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import ar.team.stockify.domain.User
import ar.team.stockify.usecases.GetUserUseCase
import ar.team.stockify.usecases.SetUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class UserViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var getUserUseCase: GetUserUseCase

    @Mock
    lateinit var setUserUseCase: SetUserUseCase

    @Mock
    lateinit var observer: Observer<UserViewModel.UiUserModel>

    private lateinit var vm: UserViewModel

    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()

    @After
    fun tearDown() {
        // 2
        Dispatchers.resetMain()
        // 3
        testDispatcher.cleanupTestCoroutines()
    }

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        vm = UserViewModel(getUserUseCase, setUserUseCase)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `when button clicked, view model value is submit`() {
        runBlockingTest {
            vm.model.observeForever(observer)

            vm.onButtonClicked()

            assertEquals(vm.model.value, UserViewModel.UiUserModel.Submit)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `when image button clicked, view model value is camera`() {
        runBlockingTest {
            vm.model.observeForever(observer)

            vm.onImageButtonClicked()

            assertEquals(vm.model.value, UserViewModel.UiUserModel.Camera)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `when save user is called, the SetUserUseCase use case is invoked`() {
        testDispatcher.runBlockingTest {
            val user = mockedUser.copy()
            whenever(setUserUseCase.invoke("Ch", "prueba.jpg")).thenReturn(user)
            vm.model.observeForever(observer)

            vm.saveUser("Ch", "prueba.jpg")

            verify(setUserUseCase).invoke("Ch", "prueba.jpg")
        }
    }

    private val mockedUser = User("Ch", "prueba.jpg")
}