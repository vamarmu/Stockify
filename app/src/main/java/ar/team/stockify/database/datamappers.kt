package ar.team.stockify.database

import ar.team.stockify.ui.model.BestMatchesDataView

fun BestMatchesDataView.toRoomStock(): Stock = Stock(
    name = name,
    symbol = symbol
)