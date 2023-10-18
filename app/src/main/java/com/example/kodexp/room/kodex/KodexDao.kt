package com.example.kodexp.room.kodex

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface KodexDao {
    @Query("SELECT * FROM kodex")
    fun getAll(): List<Kodex>

    @Query("SELECT * FROM kodex WHERE pokemon_id IN (:pokemonIds)")
    fun loadAllByIds(pokemonIds: IntArray): List<Kodex>

    @Update
    fun update(kodexEntry: Kodex)

    @Insert
    fun insertAll(vararg kodexEntries: Kodex)

    @Delete
    fun delete(kodexEntry: Kodex)
}