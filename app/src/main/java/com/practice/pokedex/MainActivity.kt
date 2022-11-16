package com.practice.pokedex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.practice.pokedex.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val recyclerViewForPokemons = binding.pokemonRecyclerList
        PokemonView(recyclerViewForPokemons)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        recyclerViewForPokemons.layoutManager = layoutManager
        recyclerViewForPokemons.setHasFixedSize(true)
        recyclerViewForPokemons.addItemDecoration(
            DividerItemDecoration(
                this,
                layoutManager.orientation
            )
        )

    }
}

class PokemonView(recyclerViewForPokemons: RecyclerView) : ViewModel(){
    init {
        getPokemonList(recyclerViewForPokemons)
    }

    private fun getPokemonList(recyclerViewForPokemons: RecyclerView) {
        viewModelScope.launch {
            try {
                val pokemons = PokemonApi.retrofitService().getPokemons();
                val pokemonAdapter = PokemonAdapter(pokemons.results)
                recyclerViewForPokemons.adapter = pokemonAdapter
                Log.d("pokemonapi", pokemons.toString())
            } catch (e: Exception) {
                Log.d("pokemonapi", "Failure: ${e.message}")
            }
        }
    }
}