package ar.team.stockify.network.model

import com.google.gson.annotations.SerializedName

data class RemoteCompany(
    @SerializedName("quarterlyEarnings")
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