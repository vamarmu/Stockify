package ar.team.stockify.testshared

import ar.team.stockify.domain.Stock
import ar.team.stockify.domain.StockDetail


    val mockedStock = Stock(
        symbol = "IBM",
        name = "International Business Machines Corp"
    )

    val mockedListStockDetail : List<StockDetail> = listOf(
        StockDetail(
          fiscalDateEnding = "2021-03-31",
          reportedDate = "2021-04-19",
          reportedEPS = "1.77",
          estimatedEPS = "1.6524",
          surprise = "0.1176",
          surprisePercentage = "7.1169"
        ),
        StockDetail(
          fiscalDateEnding = "2020-12-31",
          reportedDate = "2021-01-21",
          reportedEPS = "2.07",
          estimatedEPS = "1.8753",
          surprise = "0.1947",
          surprisePercentage = "10.3823"
        ),
        StockDetail(
          fiscalDateEnding = "2020-09-30",
          reportedDate = "2020-10-19",
          reportedEPS = "2.58",
          estimatedEPS = "2.579",
          surprise = "0.001",
          surprisePercentage = "0.0388"
        ),
        StockDetail(
          fiscalDateEnding = "2020-06-30",
          reportedDate = "2020-07-20",
          reportedEPS = "2.18",
          estimatedEPS = "2.0851",
          surprise = "0.0949",
          surprisePercentage = "4.5513"
        )
    )


