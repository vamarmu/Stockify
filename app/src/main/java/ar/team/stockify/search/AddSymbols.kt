package ar.team.stockify.search

import ar.team.stockify.model.BestMatches

interface AddSymbols {
    fun addListWithoutHeader(list: List<BestMatches>?)
    fun addListWithHeader(list: List<BestMatches>?)
}