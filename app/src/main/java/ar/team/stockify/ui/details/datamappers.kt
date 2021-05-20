package ar.team.stockify.ui.details

import ar.team.stockify.database.Stock
import ar.team.stockify.domain.BestMatches
import ar.team.stockify.ui.model.BestMatchesDataView

fun BestMatches.toBestMatchesDataView(): BestMatchesDataView = BestMatchesDataView(
    symbol = symbol,
    name = name,
)

fun Stock.toStock(): ar.team.stockify.domain.Stock = ar.team.stockify.domain.Stock(
    symbol = symbol,
    name = name
)
