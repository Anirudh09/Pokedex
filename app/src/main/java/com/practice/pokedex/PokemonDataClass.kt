package com.practice.pokedex

data class PokemonListClass(val results: List<PokemonDataClass>, val next: String)

data class PokemonDataClass(val name: String)

