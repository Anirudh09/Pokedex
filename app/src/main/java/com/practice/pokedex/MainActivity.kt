package com.practice.pokedex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
        PokemonView(recyclerViewForPokemons, this)
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

class PokemonView(recyclerViewForPokemons: RecyclerView, mainActivity: MainActivity) : ViewModel(){
    init {
        getPokemonList(recyclerViewForPokemons, mainActivity)
    }
    private fun onListItemClick(position: Int, mainActivity: MainActivity, pokemonList: List<PokemonDataClass>) {
        Toast.makeText(mainActivity, pokemonList[position].name, Toast.LENGTH_SHORT).show()
    }

    private fun getPokemonList(recyclerViewForPokemons: RecyclerView, mainActivity: MainActivity) {
        viewModelScope.launch {
            try {
                val pokemons = PokemonApi.retrofitService().getPokemons();
                val pokemonAdapter = PokemonAdapter(pokemons.results) {
                    position -> onListItemClick(position, mainActivity, pokemons.results)
                }
                recyclerViewForPokemons.adapter = pokemonAdapter
                Log.d("pokemonapi", pokemons.toString())
            } catch (e: Exception) {
                Log.d("pokemonapi", "Failure: ${e.message}")
            }
        }
    }
}