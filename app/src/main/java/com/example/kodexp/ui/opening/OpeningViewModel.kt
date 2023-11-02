import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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

class OpeningViewModel : ViewModel() {
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
                        // Affichez le sprite du Pokémon
                        _pokemonImage.value = pokemon.imageUri
                        _isImageVisible.value = true

                        // Ajoutez l'ID du Pokémon dans votre base de données en utilisant Room Database
                        val database = KodexDatabase.getInstance()
                        val kodexDao = database.kodexDao()
                        val pokemonId = pokemon.id
                        viewModelScope.launch(Dispatchers.IO) {
                            kodexDao.insertAll(Kodex(pokemonId, owned = true))
                        }
                    }
                }
            }

            override fun onFailure(call: Call<Pokemon>, t: Throwable) {
                // Gérez les erreurs ici
            }
        })
    }
}
