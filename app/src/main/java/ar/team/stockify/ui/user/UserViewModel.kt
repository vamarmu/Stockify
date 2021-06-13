package ar.team.stockify.ui.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.team.stockify.domain.User
import ar.team.stockify.usecases.GetUserUseCase
import ar.team.stockify.usecases.SetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val setUserUseCase: SetUserUseCase
) : ViewModel() {

    sealed class UiUserModel {
        object NoUser: UiUserModel()
        class Content(val user: User): UiUserModel()
        object Camera: UiUserModel()
        object Submit: UiUserModel()
    }

    private val _model = MutableLiveData<UiUserModel>()
    val model: LiveData<UiUserModel>
        get() = _model

    init {
        viewModelScope.launch {
            _model.value = getUserUseCase.invoke()?.let{ user ->
                UiUserModel.Content(user)
            }?:UiUserModel.NoUser
        }
    }

    fun saveUser (name: String, image: String){
        viewModelScope.launch {
            _model.value = UiUserModel.Content(setUserUseCase.invoke(name,image))
        }
    }

    fun onImageButtonClicked() {
        _model.value= UiUserModel.Camera
    }

    fun onButtonClicked() {
        _model.value = UiUserModel.Submit
    }

}