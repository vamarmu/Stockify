package ar.team.stockify.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.team.stockify.domain.Stock
import ar.team.stockify.usecases.GetFavouritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private var getFavouritesUseCase: GetFavouritesUseCase
): ViewModel() {
    lateinit var favouritesAdapter: FavouritesAdapter


    private fun addListWithHeader(list: List<Stock>?) {
        favouritesAdapter.addListWithHeader(list)
    }

    fun findFavourites(){
        viewModelScope.launch {
            addListWithHeader( getFavouritesUseCase.invoke())
        }
    }
}