package com.example.pagingsample.repository

import com.example.pagingsample.model.api.CharacterApi
import com.example.pagingsample.model.data.Res
import retrofit2.Response
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val characterApi: CharacterApi
) {
    suspend fun getCharacter(page: Int): Response<Res> {
        return characterApi.getCharacter(page)
    }
}