package com.practice.pokedex

import android.content.Intent
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.practice.pokedex.databinding.PokemonListBinding
import kotlinx.coroutines.launch

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
        val intent = Intent(mainActivity, MainActivity2::class.java)
        intent.putExtra("pokemon-name", pokemonList[position].name)
        mainActivity.startActivity(intent)
    }

    private fun getPokemonList(recyclerViewForPokemons: RecyclerView, mainActivity: MainActivity) {
        viewModelScope.launch {
            try {
                val pokemons = PokemonApi.retrofitService().getPokemons("pokemon-species")
                pokemonList = pokemons.results
                val pokemonAdapter = PokemonAdapter() {
                        position -> onListItemClick(position, mainActivity, pokemonList)
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

class PokemonInfoViewModel(private var pokemonName: String?): ViewModel() {
    private val _status = MutableLiveData<PokemonSpritesList>()

    // The external immutable LiveData for the request status
    val status: LiveData<PokemonSpritesList> = _status
    /**
     * Call getMarsPhotos() on init so we can display status immediately.
     */
    init {
        getPokemonInformation(pokemonName)
    }

    /**
     * Gets Mars photos information from the Mars API Retrofit service and updates the
     * [MarsPhoto] [List] [LiveData].
     */
    private fun getPokemonInformation(pokemonName: String?) {
        viewModelScope.launch {
            try {
                val listResult = PokemonApi.retrofitService().getPokemonInfo("pokemon", pokemonName)
                _status.value = listResult
                Log.d("pokemoninfo", _status.value.toString())
            } catch (e: Exception) {
                Log.d("pokemoninfo", e.message.toString())
            }
        }
    }
}