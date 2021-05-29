package ar.team.stockify.ui.details

import ar.team.stockify.database.LocalStock
import ar.team.stockify.domain.BestMatches
import ar.team.stockify.domain.Stock
import ar.team.stockify.ui.model.BestMatchesDataView

fun BestMatches.toBestMatchesDataView(): BestMatchesDataView = BestMatchesDataView(
    symbol = symbol,
    name = name,
)

fun Stock.toBestMatchesDataView(): BestMatchesDataView = BestMatchesDataView(
    symbol = symbol,
    name = name,
)

fun LocalStock.toStock(): Stock = Stock(
    symbol = symbol,
    name = name
)
