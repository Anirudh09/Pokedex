package com.practice.pokedex

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.practice.pokedex.databinding.PokemonListBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch



class PokemonAdapter(private val onItemClicked: (position: Int) -> Unit): RecyclerView.Adapter<PokemonViewModel>() {
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

class OnScrollListener(val adapter: RecyclerView.Adapter<PokemonViewModel>,
                       val pokemons: PokemonListClass) : RecyclerView.OnScrollListener() {
    var loading = true
    var updatePokemons = pokemons

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if (!recyclerView.canScrollVertically(1)) {
            getNextPokemonList(adapter, updatePokemons.next)
        }
    }

    fun getNextPokemonList(adapter: RecyclerView.Adapter<PokemonViewModel>,
                           nextSetofPokemonsUrl: String){
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val uri: Uri = Uri.parse(nextSetofPokemonsUrl)
                val pokemons = PokemonApi.retrofitService().getPokemons("pokemon-species", uri.getQueryParameter("offset"), uri.getQueryParameter("limit"))
                Log.d("pokemonapiscroll", pokemons.toString() + "\ncurrent count: " + adapter.getItemCount())
                pokemonList = pokemonList + pokemons.results
                adapter.notifyDataSetChanged()
                updatePokemons = pokemons
            } catch (e: Exception) {
                Log.d("pokemonapiscroll", "Failure: ${e.message}")
            }
        }
    }
}