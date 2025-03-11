package com.example.tarea_3_consumoapi

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("api/hello") // Endpoint de tu servicio Spring Boot
    fun getHello(): Call<Map<String, String>>
}