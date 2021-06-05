package ar.team.stockify.ui.search

interface SearchImpl {
    fun onQueryTextSubmit(filter:String)
    fun onQueryTextChange(filter: String)
}