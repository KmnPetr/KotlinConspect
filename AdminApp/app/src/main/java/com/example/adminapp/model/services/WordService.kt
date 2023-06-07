package com.example.adminapp.model.services

import com.example.adminapp.model.dto.ValidationExeptionResponseDTO
import com.example.adminapp.model.dto.WordDTO
import com.example.adminapp.model.models.Word
import com.example.adminapp.model.repositories.WebWordRepository
import com.example.adminapp.viewModels.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WordService {
    private var webWordRepository: WebWordRepository

    init {
        val interceptor= HttpLoggingInterceptor()
        interceptor.level= HttpLoggingInterceptor.Level.BODY//он будет выводить все в панель Log

        val client= OkHttpClient.Builder().addInterceptor(interceptor).build()
        /////////////////////////////////////

        webWordRepository=(
                //инициализируем ретрофит
                Retrofit.Builder()
            .baseUrl(URL)//находится пока в глобальном пространстве
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
                ).create(WebWordRepository::class.java)
    }

    /**
     * запрос на список всех Word в базе сервера
     */
    suspend fun getAllWords(): ArrayList<Word>{
        return webWordRepository.getAllWords()
    }

    /**
     * отошлет запрос на создание нового word в базе сервера
     */
    suspend fun create(token:String,
                       foreignWord:String,
                       transcription:String,
                       translation:String,
                       groupWord:String): Map<String, String?> {
        val wordDTO=WordDTO(
            null,
            foreignWord,
            transcription,
            translation,
            null,
            null,
            groupWord,
            null)

        try {
            val response=webWordRepository.create(
                "Bearer "+token,
                wordDTO
            )
            if (response.code()==200){

                return mapOf(Pair("positive_response","Saving was successful."))
            }else
            {
                val errorBody:String?=response.errorBody()?.string()//этот вызов работает только первый раз, неоюбходимо сохранение в строку

                val message= errorBody?.let { JSONObject(it).getString("message") }

                var textErrorMessage: String? =message

                //вывод полей валидации в textMessage
                if (message=="Validation Errors"){
                    val type = object : TypeToken<ValidationExeptionResponseDTO>() {}.type

                    val validationExeptionResponseDTO: ValidationExeptionResponseDTO? = Gson().fromJson(errorBody, type)

                    validationExeptionResponseDTO?.validationErrors?.forEach { (k,v)->
                        textErrorMessage+="\n$k: $v"
                    }

                }

                return mapOf(Pair("error_message",textErrorMessage))
            }
        }catch (e:Exception){
            return mapOf(Pair("connection_error","Ошибка подключения\n"+e.javaClass))
        }
    }
}