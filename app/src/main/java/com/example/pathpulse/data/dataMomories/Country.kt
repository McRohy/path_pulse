package com.example.pathpulse.data.dataMomories

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Reprezentuje záznam krajiny v databáze.
 */
@Entity(tableName = "country")
data class CountryEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val description: String?,
    val updatedAt: Long?,
    val rating: Int?
)


