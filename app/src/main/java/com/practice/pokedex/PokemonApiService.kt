package com.practice.pokedex

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val BASE_URL = "https://pokeapi.co/api/v2/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface PokemonApiService {
    /*@GET("pokemon-species")*/
    @GET("{endpoint}")
    suspend fun getPokemons(@Path("endpoint") endpoint: String,
                            @Query("offset") offset: String? = "",
                            @Query("limit") limit: String? = ""): PokemonListClass
}

object PokemonApi {
    fun retrofitService(): PokemonApiService {
        return retrofit.create(PokemonApiService::class.java)
    }
}