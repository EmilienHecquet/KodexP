package com.example.kodexp.api

import retrofit2.Response
import retrofit2.http.GET
import com.example.kodexp.model.PokemonListResponse
import retrofit2.http.Query

interface PokeApiService {
    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int): Response<PokemonListResponse>
}
