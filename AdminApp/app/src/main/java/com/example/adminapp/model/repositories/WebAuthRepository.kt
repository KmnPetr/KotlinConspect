package com.example.adminapp.model.repositories

import com.example.adminapp.model.dto.AuthenticationDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface WebAuthRepository {
    @POST("/auth/login")
    suspend fun auth(@Body authenticationDTO: AuthenticationDTO): Response<Map<String,String>>//вернет обьект или ошибку
}