package ar.team.stockify.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Stock (
    @PrimaryKey
    val symbol: String,
    val name: String
)