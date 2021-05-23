package ar.team.stockify.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.team.stockify.domain.BestMatches
import ar.team.stockify.domain.Stock
import ar.team.stockify.usecases.GetFavouritesUseCase
import kotlinx.coroutines.launch

class FragmentViewModel(
    private var getFavouritesUseCase: GetFavouritesUseCase
): ViewModel() {

    private var _items = listOf<BestMatches>()

    lateinit var adapter: SearchAdapter
    lateinit var favouritesadapter: FavouritesAdapter

    init {
        viewModelScope.launch {
            addListWithHeader(getFavouritesUseCase.invoke())
        }

    }
    private fun addListWithHeader(list: List<Stock>?) {
        favouritesadapter.addListWithHeader(list)
    }
}