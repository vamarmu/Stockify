package ar.team.stockify.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class SearchViewModel: SearchImpl {
    val _adapter= MutableLiveData<SearchAdapter>()
    val adapter:LiveData<SearchAdapter>
    get() = _adapter

    override fun onQueryTextSubmit(filter:String) {
        _adapter.value?.onQueryTextSubmit(filter)
    }

    override fun onQueryTextChange(filter: String) {
        _adapter.value?.onQueryTextChange(filter)
    }


}