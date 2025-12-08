package com.example.belajar_api.repository

import com.example.belajar_api.entity.Catatan
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface CatatanRepository {
    @POST("catatan")
    suspend fun createCatatan(@Body catatan: Catatan ): Response<Catatan>

    @GET("catatan")
    suspend fun getCatatan(): Response<List<Catatan>>
}