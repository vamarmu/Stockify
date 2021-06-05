package ar.team.stockify.network

import ar.team.stockify.domain.BestMatches
import ar.team.stockify.domain.Company
import ar.team.stockify.domain.QuarterlyEarning
import ar.team.stockify.domain.Symbols
import ar.team.stockify.model.RemoteBestMatches
import ar.team.stockify.model.RemoteSymbol
import ar.team.stockify.network.model.RemoteCompany
import ar.team.stockify.network.model.RemoteQuarterlyEarning

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

fun RemoteCompany.toCompany() : Company = Company(
    quarterlyEarnings = this.quarterlyEarnings.map { it.toQuarterlyEarning()}.toList()
)

fun RemoteQuarterlyEarning.toQuarterlyEarning() : QuarterlyEarning = QuarterlyEarning(
    estimatedEPS = estimatedEPS,
    fiscalDateEnding = fiscalDateEnding,
    reportedDate = reportedDate,
    reportedEPS = reportedEPS,
    surprise = surprise,
    surprisePercentage = surprisePercentage
)