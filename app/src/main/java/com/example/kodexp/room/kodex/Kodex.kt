package com.example.kodexp.room.kodex

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Kodex(
    @PrimaryKey val pokemon_id: Int,
    @ColumnInfo(name = "owned") val owned: Boolean = false
)