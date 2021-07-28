package ar.team.stockify.ui.details

import ar.team.stockify.database.LocalStock
import ar.team.stockify.domain.Stock
import ar.team.stockify.ui.model.BestMatchesDataView
import ar.team.stockify.ui.model.StockDataView

fun StockDataView.toStock(): Stock = Stock(
    symbol = symbol,
    name = name,
)

fun Stock.toStockDataView(): StockDataView = StockDataView(
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

fun Stock.toLocalStock(): LocalStock = LocalStock(
    symbol = symbol,
    name = name
)


