package ar.team.stockify.ui.search

import ar.team.stockify.domain.BestMatches


interface AddSymbols {
    fun addListWithoutHeader(list: List<BestMatches>?)
    fun addListWithHeader(list: List<BestMatches>?)
}