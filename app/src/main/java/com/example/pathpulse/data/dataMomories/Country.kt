package com.example.pathpulse.data.dataMomories

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "country")
data class CountryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String
)


