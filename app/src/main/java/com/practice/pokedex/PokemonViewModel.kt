package com.practice.pokedex

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.practice.pokedex.databinding.PokemonListBinding

class PokemonViewModel(private val binding: PokemonListBinding,
                       private val onItemClicked: (position: Int) -> Unit): RecyclerView.ViewHolder(binding.root), View.OnClickListener {
    init {
        itemView.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        val position = adapterPosition
        onItemClicked(position)
    }

    fun bind(pokemonList: PokemonDataClass) {
        binding.pokemonList = pokemonList
    }
}