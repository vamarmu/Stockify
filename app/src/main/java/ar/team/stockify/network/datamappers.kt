package ar.team.stockify.network


import ar.team.stockify.domain.Stock

import ar.team.stockify.network.model.RemoteBestMatches


fun RemoteBestMatches.toStock() : Stock = Stock(
    symbol = symbol,
    name = name,
)

/*fun RemoteBestMatches.toBestMatches(): BestMatches = BestMatches(
    symbol = symbol,
    name = name,
    type = type,
    region = region,
    marketOpen = marketOpen,
    marketClose = marketClose,
    timezone = timezone,
    currency = currency,
    matchScore = matchScore
)*/

