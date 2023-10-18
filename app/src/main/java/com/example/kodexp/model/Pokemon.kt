package com.example.kodexp.model

data class Pokemon(val name: String, val url: String)

data class PokemonListResponse(
    val results: List<PokemonListItem>
)

data class PokemonListItem(
    val name: String,
    val url: String
)