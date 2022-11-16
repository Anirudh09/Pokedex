package com.practice.pokedex

import androidx.recyclerview.widget.RecyclerView
import com.practice.pokedex.databinding.PokemonListBinding

class PokemonViewModel(private val binding: PokemonListBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(pokemonList: PokemonDataClass) {
        binding.pokemonList = pokemonList
    }
}