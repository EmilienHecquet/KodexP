package com.example.kodexp.model

import java.lang.reflect.Constructor

private fun urlToId(url: String): Int {
    return "/-?[0-9]+/$".toRegex().find(url)!!.value.filter { it.isDigit() || it == '-' }.toInt()
}
class Pokemon(val name: String, val url: String, var owned: Boolean = false){
    val id by lazy { urlToId(url) }

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