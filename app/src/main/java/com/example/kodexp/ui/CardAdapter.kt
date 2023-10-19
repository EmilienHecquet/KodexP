package com.example.kodexp.ui

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.kodexp.R
import com.example.kodexp.model.Pokemon


class CardAdapter(val cardItemList: List<Pokemon>): RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    class CardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var pokemonTextTitle: TextView
        var pokemonEntryNumber: TextView
        var pokemonUriImage : ImageView
        var ownedImage : ImageView

        init {
            pokemonTextTitle = view.findViewById<TextView>(R.id.pokemonTextTitle)
            pokemonEntryNumber = view.findViewById<TextView>(R.id.pokemonEntryNumber)
            pokemonUriImage = view.findViewById<ImageView>(R.id.pokemonUriImage)
            ownedImage = view.findViewById<ImageView>(R.id.ownedImage)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.card_view_layout, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val currentItem: Pokemon = cardItemList[position]
        holder.pokemonTextTitle.setText(currentItem.name)
        holder.pokemonEntryNumber.setText(currentItem.id.toString())
        holder.pokemonUriImage.load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/132.png")
        if (!currentItem.owned) holder.ownedImage.visibility = View.GONE
    }

    override fun getItemCount(): Int {
        return cardItemList.size
    }
}