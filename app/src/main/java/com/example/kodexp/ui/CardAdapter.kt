package com.example.kodexp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kodexp.R
import com.example.kodexp.room.kodex.Kodex


class CardAdapter(val cardItemList: List<Kodex>?): RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    class CardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var textViewTitle: TextView
        var textViewDescription: TextView

        init {
            textViewTitle = view.findViewById<TextView>(R.id.textViewTitle)
            textViewDescription = view.findViewById<TextView>(R.id.textViewDescription)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.card_view_layout, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val currentItem: Kodex = cardItemList!![position]
        holder.textViewTitle.setText(currentItem.pokemon_id.toString())
        holder.textViewDescription.setText(currentItem.owned.toString())
    }

    override fun getItemCount(): Int {
        return cardItemList!!.size
    }
}