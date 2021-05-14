package ar.team.stockify.search

interface SearchImpl {
    fun onQueryTextSubmit(filter:String)
    fun onQueryTextChange(filter: String)
}