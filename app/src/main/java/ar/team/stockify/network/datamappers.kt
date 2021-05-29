package ar.team.stockify.network

import ar.team.stockify.domain.BestMatches
import ar.team.stockify.domain.Symbols
import ar.team.stockify.network.model.RemoteBestMatches
import ar.team.stockify.network.model.RemoteSymbol

fun RemoteSymbol.toSymbols() : Symbols = Symbols(
    bestMatches = this.bestMatches.map { it.toBestMatches()}.toList()
)

fun RemoteBestMatches.toBestMatches(): BestMatches = BestMatches(
    symbol = symbol,
    name = name,
    type = type,
    region = region,
    marketOpen = marketOpen,
    marketClose = marketClose,
    timezone = timezone,
    currency = currency,
    matchScore = matchScore
)

