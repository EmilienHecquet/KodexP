package com.example.kodexp.model

import java.lang.reflect.Constructor
import com.example.kodexp.utils.urlToId

class Pokemon(val name: String, val url: String, var owned: Boolean = false){
    val id by lazy { urlToId(url) }
    val imageUri by lazy { "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png" }

    fun updateOwned(_owned: Boolean): Pokemon {
        owned = _owned
        return this
    }
}

data class PokemonListResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonListItem>
)

data class PokemonListItem(
    val name: String,
    val url: String,
    val owned: Boolean
)