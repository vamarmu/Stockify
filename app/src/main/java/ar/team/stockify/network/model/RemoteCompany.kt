package ar.team.stockify.network.model

data class RemoteCompany(
    val remoteQuarterlyEarnings: List<RemoteQuarterlyEarning>,
    val symbol: String
)

data class RemoteQuarterlyEarning(
    val estimatedEPS: String,
    val fiscalDateEnding: String,
    val reportedDate: String,
    val reportedEPS: String,
    val surprise: String,
    val surprisePercentage: String
)