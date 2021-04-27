package ar.team.stockify.search

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class SearchViewModelFactory(
):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SearchViewModel::class.java)){
            return SearchViewModel() as T
        }
        throw IllegalArgumentException("Unkown ViewModel class received")
    }
}