package com.practice.pokedex

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.practice.pokedex.databinding.PokemonListBinding

class PokemonAdapter(private val pokemonList: List<PokemonDataClass>, private val onItemClicked: (position: Int) -> Unit): RecyclerView.Adapter<PokemonViewModel>(){
    private lateinit var binding: PokemonListBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewModel {
        binding = PokemonListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokemonViewModel(binding, onItemClicked)
    }

    override fun onBindViewHolder(holder: PokemonViewModel, position: Int) {
        val pokemonListItems = pokemonList[position]
        holder.bind(pokemonListItems)
    }

    override fun getItemCount(): Int = pokemonList.size
}