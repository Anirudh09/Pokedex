package com.practice.pokedex

data class PokemonListClass(val results: List<PokemonDataClass>, val next: String)

data class PokemonDataClass(val name: String)

data class PokemonSpritesList(val sprites: PokemonImage)

data class PokemonImage(val front_default: String)