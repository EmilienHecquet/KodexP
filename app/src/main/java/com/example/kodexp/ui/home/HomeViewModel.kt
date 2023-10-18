package com.example.kodexp.ui.home

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.room.Room
import com.example.kodexp.room.kodex.Kodex
import com.example.kodexp.room.kodex.KodexDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.kodexp.model.Pokemon
import com.example.kodexp.api.PokeApiService
import kotlinx.coroutines.withContext

class HomeViewModel(context: Context) : ViewModel() {

    val db: KodexDatabase
    private val _pokemonList = MutableLiveData<List<Pokemon>>()
    val pokemonList: LiveData<List<Pokemon>> get() = _pokemonList

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://pokeapi.co/api/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create(PokeApiService::class.java)
    private val _pokemons = MutableLiveData<List<Kodex>>()
    val pokemons: LiveData<List<Kodex>> = _pokemons

    companion object {

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("HomeViewModel")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                // Get the Application object from extras
                val application = checkNotNull(extras[APPLICATION_KEY])

                return HomeViewModel(
                    (application as Application).applicationContext
                ) as T
            }
        }
    }

    init {
        // Create the Room database instance and add prepropulate data
        db = Room.databaseBuilder(
            context.applicationContext,
            KodexDatabase::class.java, "kodex-database"
        ).build()

        fetchPokemonData()

        viewModelScope.launch(Dispatchers.IO) {
            //TODO : appel api
            //todo: traitement

            var result = db.kodexDao().getAll()
            withContext(Dispatchers.Main) {
                Log.d("TAG", "init: $result")
                _pokemons.value = result
            }
        }
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
