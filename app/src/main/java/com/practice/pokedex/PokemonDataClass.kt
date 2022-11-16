package com.practice.pokedex

data class PokemonListClass(val results: List<PokemonDataClass>)

data class PokemonDataClass(val name: String)