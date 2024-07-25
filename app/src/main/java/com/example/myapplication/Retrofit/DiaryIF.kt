package com.example.myapplication.Retrofit


import com.example.myapplication.Data.Response.FavoriteResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Call

interface DiaryIF {
    @GET("/api/memory")
    fun getFavorite(
        @Query("viewType") viewType: String
    ):Call<FavoriteResponse>
}