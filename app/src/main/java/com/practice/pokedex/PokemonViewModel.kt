package com.practice.pokedex

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.practice.pokedex.databinding.PokemonListBinding
import kotlinx.coroutines.launch
import androidx.recyclerview.widget.LinearLayoutManager

lateinit var pokemonList: List<PokemonDataClass>

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

class PokemonView(mainActivity: MainActivity, recyclerViewForPokemons: RecyclerView) : ViewModel(){

    init {
        getPokemonList(recyclerViewForPokemons, mainActivity)
    }

    fun onListItemClick(position: Int, mainActivity: MainActivity, pokemonList: List<PokemonDataClass>) {
        Toast.makeText(mainActivity, pokemonList[position].name, Toast.LENGTH_SHORT).show()
    }

    private fun getPokemonList(recyclerViewForPokemons: RecyclerView, mainActivity: MainActivity) {
        viewModelScope.launch {
            try {
                val pokemons = PokemonApi.retrofitService().getPokemons("pokemon-species")
                pokemonList = pokemons.results
                val pokemonAdapter = PokemonAdapter() {
                        position -> onListItemClick(position, mainActivity, pokemons.results)
                }
                recyclerViewForPokemons.adapter = pokemonAdapter
                recyclerViewForPokemons.addOnScrollListener(OnScrollListener(pokemonAdapter,
                                                            pokemons))
                Log.d("pokemonapi", pokemons.toString())
            } catch (e: Exception) {
                Log.d("pokemonapi", "Failure: ${e.message}")
            }
        }
    }
}