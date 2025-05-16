package com.example.pathpulse.data.dataExplorer

/**
 * Reprezentuje položku v prehliadači krajín (Exploreri).
 *
 * @property imageRes ID zdroja obrázka krajiny.
 * @property titleRes ID zdroja názvu krajiny.
 * @property descRes ID zdroja popisu krajiny.
 */
data class CountryExplorer(
    val imageRes: Int,
    val titleRes: Int,
    val descRes: Int
)