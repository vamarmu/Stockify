package ar.team.stockify.ui.user

import androidx.lifecycle.*
import ar.team.stockify.data.repository.UserRepository
import ar.team.stockify.domain.User

import ar.team.stockify.model.Company
import ar.team.stockify.model.QuarterlyEarning
import ar.team.stockify.network.AlphaVantage
import ar.team.stockify.network.Keys
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    userRepository: UserRepository
) : ViewModel() {

    sealed class UiUserModel {
        object NoUser: UiUserModel()
        class Content(user: User): UiUserModel()
        object Camera: UiUserModel()
        object Submit: UiUserModel()
    }

    private val _model = MutableLiveData<UiUserModel>()
    val model: LiveData<UiUserModel>
        get() = _model

    init {
        if(userRepository.hasUser()) {
            _model.value = UiUserModel.Content(userRepository.getUser())
        } else _model.value = UiUserModel.NoUser
    }

    fun onImageButtonClicked() = UiUserModel.Camera

    fun onButtonClicked() = UiUserModel.Submit
}




