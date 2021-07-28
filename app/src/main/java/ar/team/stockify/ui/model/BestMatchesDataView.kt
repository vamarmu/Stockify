package ar.team.stockify.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BestMatchesDataView(
    val symbol: String,
    val name: String,
): Parcelable