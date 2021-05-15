package ar.team.stockify.domain

data class Company(
    val annualEarnings: List<AnnualEarning>,
    val quarterlyEarnings: List<QuarterlyEarning>,
    val symbol: String
)

data class AnnualEarning(
    val fiscalDateEnding: String,
    val reportedEPS: String
)

data class QuarterlyEarning(
    val estimatedEPS: String,
    val fiscalDateEnding: String,
    val reportedDate: String,
    val reportedEPS: String,
    val surprise: String,
    val surprisePercentage: String
)