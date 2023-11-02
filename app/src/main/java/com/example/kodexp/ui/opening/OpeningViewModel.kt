package com.example.kodexp.ui.opening

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.kodexp.model.Pokemon
import com.example.kodexp.room.kodex.Kodex
import com.example.kodexp.room.kodex.KodexDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface PokeApiService {
    @GET("pokemon/{pokemonId}")
    fun getPokemon(@Path("pokemonId") pokemonId: Int): Call<Pokemon>
}

class OpeningViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OpeningViewModel::class.java)) {
            return OpeningViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class OpeningViewModel(private val context: Context) : ViewModel() {
    private val _isImageVisible = MutableLiveData(false)
    val isImageVisible: LiveData<Boolean> = _isImageVisible

    private val _pokemonImage = MutableLiveData<String>()
    val pokemonImage: LiveData<String> = _pokemonImage

    fun onRandomPokemonButtonClick() {
        val randomPokemonId = (1..898).random()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(PokeApiService::class.java)
        val call = apiService.getPokemon(randomPokemonId)

        call.enqueue(object : Callback<Pokemon> {
            override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                if (response.isSuccessful) {
                    val pokemon = response.body()
                    if (pokemon != null) {
                        // Display the Pokémon's sprite
                        _pokemonImage.value = pokemon.imageUri
                        _isImageVisible.value = true

                        // Add the Pokémon's ID to your database using Room Database
                        val database = KodexDatabase.getInstance(context)
                        val kodexDao = database.kodexDao()
                        val pokemonId = pokemon.id
                        viewModelScope.launch(Dispatchers.IO) {
                            kodexDao.insertAll(Kodex(pokemonId, owned = true))
                        }
                    }
                }
            }

            override fun onFailure(call: Call<Pokemon>, t: Throwable) {
                // Handle errors here
            }
        })
    }
}
