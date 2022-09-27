package com.practice.pokedex

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/**
 * The [ViewModel] that is attached to the [OverviewFragment].
 */
class PokemonViewModel : ViewModel() {

    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<String>()

    // The external immutable LiveData for the request status
    val status: LiveData<String> = _status
    /**
     * Call getMarsPhotos() on init so we can display status immediately.
     */
    init {
        getPokemonList()
    }

    /**
     * Gets Mars photos information from the Mars API Retrofit service and updates the
     * [MarsPhoto] [List] [LiveData].
     */
    private fun getPokemonList() {
        viewModelScope.launch {
            try {
                val listResult = PokemonApi.retrofitService.getPokemons()
                _status.value = listResult
                Log.d("pokemonapi", _status.value.toString())
            } catch (e: Exception) {
                _status.value = "Failure: ${e.message}"
            }
        }
    }
}