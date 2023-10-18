package com.example.kodexp.ui.home

import android.app.Application
import android.content.ContentValues
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
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.kodexp.room.kodex.Kodex
import com.example.kodexp.room.kodex.KodexDao
import com.example.kodexp.room.kodex.KodexDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.security.auth.callback.Callback

class HomeViewModel(context: Context) : ViewModel() {

    val db: KodexDatabase
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

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
}