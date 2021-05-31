package ar.team.stockify.ui.user


import androidx.lifecycle.*
import ar.team.stockify.data.repository.StockifyRepository
import ar.team.stockify.domain.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    // TODO quitar userRepository y añadir el caso de uso
    // getUserUserCase
    //setUserUSerCase
    private val userRepository: StockifyRepository
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
            _model.value = userRepository.getUser()?.let{ user ->
                UiUserModel.Content(user)
            }?:UiUserModel.NoUser
        }

    }

    fun saveUser (name: String, image: String){
        viewModelScope.launch {
            _model.value = UiUserModel.Content(userRepository.setUser(name,image))
        }
    }

    fun onImageButtonClicked() {
        _model.value= UiUserModel.Camera
    }


    fun onButtonClicked() {
        _model.value = UiUserModel.Submit
    }

}




