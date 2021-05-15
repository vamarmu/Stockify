package ar.team.stockify.domain

data class Symbols(
    val bestMatches: List<BestMatches>
)

data class BestMatches(
    val symbol: String,
    val name: String,
    val type: String,
    val region: String,
    val marketOpen: String,
    val marketClose: String,
    val timezone: String,
    val currency: String,
    val matchScore: String
)
