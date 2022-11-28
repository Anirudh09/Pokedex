package com.practice.pokedex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import coil.load
import com.practice.pokedex.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val intent = intent
        val pokemonClicked = intent.getStringExtra("pokemon-name")
        supportActionBar?.title = pokemonClicked
        val pokemonInfoViewModel = PokemonInfoViewModel(pokemonClicked)
        val binding: ActivityMain2Binding = DataBindingUtil.setContentView(this, R.layout.activity_main2)
        binding.pokemonInfo = pokemonInfoViewModel
        binding.lifecycleOwner = this
    }
}