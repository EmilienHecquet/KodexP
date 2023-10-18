package com.example.kodexp.api

import retrofit2.Response
import retrofit2.http.GET
import com.example.kodexp.model.PokemonListResponse

interface PokeApiService {
    @GET("pokemon")
    suspend fun getPokemonList(): Response<PokemonListResponse>
}
