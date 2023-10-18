package com.example.kodexp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.kodexp.model.Pokemon
import com.example.kodexp.api.PokeApiService
import kotlinx.coroutines.launch
import android.util.Log



class HomeViewModel : ViewModel() {
    private val _pokemonList = MutableLiveData<List<Pokemon>>()
    val pokemonList: LiveData<List<Pokemon>> get() = _pokemonList

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://pokeapi.co/api/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create(PokeApiService::class.java)

    init {
        fetchPokemonData()
    }

    private fun fetchPokemonData() {
        viewModelScope.launch {
            try {
                val response = service.getPokemonList()
                if (response.isSuccessful) {
                    val pokemonListResponse = response.body()
                    val pokemonList = pokemonListResponse?.results?.map { pokemonListItem ->
                        Pokemon(pokemonListItem.name, pokemonListItem.url)
                    } ?: emptyList()

                    _pokemonList.value = pokemonList

                    // Log the list of Pokémon
                    for (pokemon in pokemonList) {
                        Log.d("HomeViewModel", "Pokemon Name: ${pokemon.name}")
                    }

                } else {
                    // Handle the error here
                }
            } catch (e: Exception) {
                // Handle the exception here
            }
        }
    }
}
