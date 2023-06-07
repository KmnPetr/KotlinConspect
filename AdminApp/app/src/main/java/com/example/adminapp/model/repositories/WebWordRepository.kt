package com.example.adminapp.model.repositories

import com.example.adminapp.model.dto.WordDTO
import com.example.adminapp.model.models.Word
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface WebWordRepository {
    /**
     * запрос на список всех Word в базе сервера
     */
    @GET("/words")
    suspend fun getAllWords():ArrayList<Word>

    /**
     * отправит запрос на добавление нового word на сервер
     */
    @POST("/words/create")
    suspend fun create(@Header("Authorization")token:String,
        @Body wordDTO: WordDTO):Response<Unit>
}