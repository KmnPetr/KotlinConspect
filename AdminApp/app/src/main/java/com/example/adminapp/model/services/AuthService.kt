package com.example.adminapp.model.services

import com.example.adminapp.model.dto.AuthenticationDTO
import com.example.adminapp.model.repositories.WebAuthRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.ConnectException
import java.net.SocketTimeoutException

class AuthService {
    private var webAuthRepository: WebAuthRepository

    init {
        val interceptor= HttpLoggingInterceptor()
        interceptor.level= HttpLoggingInterceptor.Level.BODY//он будет выводить все в панель Log

        val client= OkHttpClient.Builder().addInterceptor(interceptor).build()
        /////////////////////////////////////

        //инициализируем ретрофит
        val retrofit= Retrofit.Builder()
            .baseUrl(URL)//находится пока в глобальном пространстве
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        //инициализируем интерфейс WebAuthRepository
        webAuthRepository=retrofit.create(WebAuthRepository::class.java)//сделали из интерфейса класс
    }

    /**
     * функция аутентификации
     */
    suspend fun authentication(email:String, password:String):Map<String,String>{
        try {
            val response=webAuthRepository.auth(AuthenticationDTO(email,password))
            //val message=JSONObject(response.errorBody()?.string()).getString("massage")//так было до исправления
            val message= response.errorBody()?.string()?.let { JSONObject(it).getString("massage") }//если errorBody не будет пустым, то запустит JSONObject

            if (message!=null) {
                return mapOf(Pair("massage", message))
            } else {
                return mapOf(Pair("jwt-token", response.body()?.get("jwt-token").orEmpty()))
            }
        }catch (e:Exception){
            if (e is ConnectException) return mapOf(Pair("connection_error","There is no internet connection."))
            else if (e is SocketTimeoutException) return mapOf(Pair("connection_error","The server is temporarily unavailable."))
            else return mapOf(Pair("connection_error","Lost connection with the server."))
        }
    }
}