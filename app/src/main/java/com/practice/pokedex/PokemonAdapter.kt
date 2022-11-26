package com.practice.pokedex

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
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

class OnScrollListener(val layoutManager: LinearLayoutManager,
                       val adapter: RecyclerView.Adapter<PokemonViewModel>) : RecyclerView.OnScrollListener() {
    var previousTotal = 0
    var loading = true
    val visibleThreshold = 10
    var firstVisibleItem = 0
    var visibleItemCount = 0
    var totalItemCount = 0

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

//        visibleItemCount = recyclerView.childCount
//        totalItemCount = layoutManager.itemCount
//        firstVisibleItem = layoutManager.findFirstVisibleItemPosition()

//        if (loading) {
//            if (totalItemCount > previousTotal) {
//                loading = false
//                previousTotal = totalItemCount
//            }
//        }

//        if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold))
//            getNextPokemonList(adapter)
        if (!recyclerView.canScrollVertically(1)) {
            getNextPokemonList(adapter)
        }
        /*Log.d("slice", pokemonList.next.slice(BASE_URL.length until pokemonList.next.length))*/
    }

    fun getNextPokemonList(adapter: RecyclerView.Adapter<PokemonViewModel>){
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val pokemons = PokemonApi.retrofitService().getPokemons("pokemon-species", "20", "20")
                Log.d("pokemonapiscroll", pokemons.toString() + "\ncurrent count: " + adapter.getItemCount())
                pokemonList = pokemonList + pokemons.results
                adapter.notifyDataSetChanged()
//                pokemonListItems.addAll(pokemons.results)
                loading = true
            } catch (e: Exception) {
                Log.d("pokemonapiscroll", "Failure: ${e.message}")
            }
        }
    }
}