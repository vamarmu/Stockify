package ar.team.stockify.network


import ar.team.stockify.domain.Stock
import ar.team.stockify.domain.StockDetail

import ar.team.stockify.network.model.RemoteBestMatches
import ar.team.stockify.network.model.RemoteCompany
import ar.team.stockify.network.model.RemoteQuarterlyEarning


fun RemoteBestMatches.toStock() : Stock = Stock(
    symbol = symbol,
    name = name,
)

fun RemoteCompany?.toListStockDetail() : List<StockDetail>? =
    this?.remoteQuarterlyEarnings?.map {it.toStockDetail() }



fun RemoteQuarterlyEarning.toStockDetail(): StockDetail = StockDetail(
    estimatedEPS =  estimatedEPS,
    fiscalDateEnding = fiscalDateEnding,
    reportedDate = reportedDate,
    surprise = surprise,
    surprisePercentage = surprisePercentage,
    reportedEPS = reportedEPS
)

