package com.example.pathpulse.data.dataExplorer

import com.example.pathpulse.R

/**
 * Zdroj dát pre sekciu Explorer.
 * Obsahuje zoznam položiek typu CountryExplorer, ktoré definujú
 * obrázok, nadpis a popis každej krajiny.
 */
object DataSource {
    /**
     * Zoznam krajín na zobrazovanie v UI.
     * Každá položka obsahuje: obrázok, reťazec pre názov krajiny a pre popis krajiny.
     */
    val countries = listOf(
        CountryExplorer(R.drawable.explorer_uae_lowquality_blur, R.string.uae_TOP, R.string.uae_desc),
        CountryExplorer(R.drawable.explorer_usa_lowquality_blur, R.string.usa_TOP, R.string.usa_desc),
        CountryExplorer(R.drawable.explorer_france_lowquality_blur, R.string.france_TOP, R.string.france_desc),
        CountryExplorer(R.drawable.explorer_finland_lowquality_blur, R.string.finland_TOP, R.string.finland_desc),
        CountryExplorer(R.drawable.explorer_norway_lowquality_blur, R.string.norway_TOP,R.string.norway_desc),
        CountryExplorer(R.drawable.explorer_uk_lowquality_blur, R.string.uk_TOP,R.string.uk_desc),
        CountryExplorer(R.drawable.explorer_spain_lowquality_blur, R.string.spain_TOP,R.string.spain_desc),
        CountryExplorer(R.drawable.explorer_italy_lowquality_blur, R.string.italy_TOP,R.string.italy_desc),
        CountryExplorer(R.drawable.explorer_morocco_lowquality_blur, R.string.morocco_TOP,R.string.morocco_desc),
    )
}