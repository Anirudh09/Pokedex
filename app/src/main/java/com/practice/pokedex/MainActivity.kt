package com.practice.pokedex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.practice.pokedex.databinding.ActivityMainBinding
/*import com.practice.pokedex.OnScrollListener*/

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val recyclerViewForPokemons = binding.pokemonRecyclerList
        PokemonView(this, recyclerViewForPokemons)
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