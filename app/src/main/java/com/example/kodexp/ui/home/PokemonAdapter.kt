package com.example.kodexp.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kodexp.model.Pokemon
import com.example.kodexp.databinding.ItemPokemonBinding


class PokemonAdapter : RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {
    private var pokemonList: List<Pokemon> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val binding = ItemPokemonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokemonViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bind(pokemonList[position])
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }

    inner class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemPokemonBinding.bind(itemView)

        fun bind(pokemon: Pokemon) {
            binding.textViewPokemonName.text = pokemon.url
        }
    }


    fun updateData(newData: List<Pokemon>) {
        pokemonList = newData
        notifyDataSetChanged()
    }
}
