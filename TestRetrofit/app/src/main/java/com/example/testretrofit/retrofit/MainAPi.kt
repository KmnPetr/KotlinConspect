package com.example.testretrofit.retrofit

import com.example.testretrofit.models.AuthRequest
import com.example.testretrofit.models.Product
import com.example.testretrofit.models.Products
import com.example.testretrofit.models.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MainAPi {
    @GET("/auth/products/{id}")//не заработает без токена
    suspend fun getProductById(@Path("id") id: Int): Product

    @POST("/auth/login")
    suspend fun auth(@Body authRequest: AuthRequest): Response<User>//вернет обьект или ошибку

    @GET("/auth/products")//не заработает без токена
    suspend fun getAllProducts(): Products

    @Headers(
        "Content-Type: application/json"
    //можно еще добавить статических заголовков
    )
    @GET("/auth/products/search")
    suspend fun getSearchProducts(@Header("Authorization")token:String,
                                  @Query("q")name:String): Products
}