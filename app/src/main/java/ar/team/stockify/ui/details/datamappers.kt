package ar.team.stockify.ui.details

import ar.team.stockify.domain.BestMatches
import ar.team.stockify.ui.model.BestMatchesDataView

fun BestMatches.toBestMatchesDataView() : BestMatchesDataView = BestMatchesDataView(
    symbol = symbol,
    name = name,
)