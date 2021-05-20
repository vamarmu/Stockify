package ar.team.stockify.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ar.team.stockify.usecases.GetFavouritesUseCase
import ar.team.stockify.usecases.GetStocksUseCase
import java.lang.IllegalArgumentException

class SearchViewModelFactory(
    private val getStocksUseCase: GetStocksUseCase,
    private val getFavouritesUseCase: GetFavouritesUseCase
):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SearchViewModel::class.java)){
            return SearchViewModel(getStocksUseCase = getStocksUseCase, getFavouritesUseCase=getFavouritesUseCase) as T
        }
        throw IllegalArgumentException("Unkown ViewModel class received")
    }
}