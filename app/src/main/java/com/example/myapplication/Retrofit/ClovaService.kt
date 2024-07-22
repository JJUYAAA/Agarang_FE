package com.example.myapplication.Retrofit

import com.example.myapplication.Data.Response.ClovaResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ClovaService {
    @Multipart
    @POST("/recog/v1/stt")
    suspend fun uploadAudio(
        @Part audio: MultipartBody.Part,
        @Part("X-NCP-APIGW-API-KEY-ID") clientId: RequestBody,
        @Part("X-NCP-APIGW-API-KEY") clientSecret: RequestBody
    ): ClovaResponse
}