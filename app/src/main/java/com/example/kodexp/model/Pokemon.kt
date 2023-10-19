package com.example.kodexp.model

class Pokemon(val id: Int,val name: String, val url: String, val owned: Boolean = false){
    fun updateOwned(owned: Boolean): Pokemon {
        return Pokemon(id, name, url, owned)
    }
}

data class PokemonListResponse(
    val results: List<PokemonListItem>
)

data class PokemonListItem(
    val name: String,
    val url: String,
    val owned: Boolean
)