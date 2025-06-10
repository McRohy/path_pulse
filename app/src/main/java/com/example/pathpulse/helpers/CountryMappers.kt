package com.example.pathpulse.helpers

import com.example.pathpulse.data.dataMomories.CountryEntity
import com.example.pathpulse.screens.memories.CountryDetails


/**
 * Prevod medzi doménovým modelom CountryDetails a DB entitou CountryEntity.
 */
object CountryMappers {

    fun CountryDetails.toEntity(): CountryEntity =
        CountryEntity(
            id          = id,
            name        = name,
            description = description,
            updatedAt   = updatedAt,
            rating      = rating,
            imgUri      = imgUri
        )

    fun CountryEntity.toDetails(): CountryDetails =
        CountryDetails(
            id          = id,
            name        = name,
            description = description.orEmpty(),
            updatedAt   = updatedAt ?: 0L,
            rating      = rating ?: 0,
            imgUri      = imgUri.orEmpty()
        )
}