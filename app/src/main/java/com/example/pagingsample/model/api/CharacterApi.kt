package com.example.pagingsample.model.api

import com.example.pagingsample.model.data.Res
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterApi {
    @GET("api/character")
    suspend fun getCharacter(@Query("page") page: Int): Response<Res>
}