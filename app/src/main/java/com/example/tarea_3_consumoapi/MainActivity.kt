package com.example.tarea_3_consumoapi

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var textViewMessage: TextView
    private lateinit var buttonGetMessage: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Vincular el TextView y el Button
        textViewMessage = findViewById(R.id.textViewMessage)
        buttonGetMessage = findViewById(R.id.buttonGetMessage)

        // Configurar Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/") // URL base de tu servidor Spring Boot
            .addConverterFactory(GsonConverterFactory.create()) // Conversor de JSON a objetos Kotlin
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        // Configurar el clic del botón
        buttonGetMessage.setOnClickListener {
            // Realizar la petición HTTP
            apiService.getHello().enqueue(object : Callback<Map<String, String>> {
                override fun onResponse(call: Call<Map<String, String>>, response: Response<Map<String, String>>) {
                    if (response.isSuccessful) {
                        // Procesar la respuesta JSON
                        val message = response.body()?.get("message")
                        textViewMessage.text = message
                    } else {
                        textViewMessage.text = "Error: ${response.code()}"
                    }
                }

                override fun onFailure(call: Call<Map<String, String>>, t: Throwable) {
                    textViewMessage.text = "Error: ${t.message}"
                }
            })
        }
    }
}