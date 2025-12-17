package com.example.belajar_api.api

import com.example.belajar_api.repository.CatatanRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "http://192.168.0.9:8000/api/"

    val catatanRepository: CatatanRepository by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CatatanRepository::class.java)
    }
}