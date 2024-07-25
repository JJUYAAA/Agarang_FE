package com.example.myapplication.Retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object FavoriteRetrofit {
    private const val BASE_URL = "https://www.agarang.site/"

    private val retrofit by lazy{
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val FavoriteService: DiaryIF by lazy{
        retrofit.create(DiaryIF::class.java)
    }
}