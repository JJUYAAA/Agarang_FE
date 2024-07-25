package com.example.myapplication.Data.Response

public final data class FavoriteResponse(
    val isSuccess: Boolean,
    val code: Int,
    val message: String,
    val result: FavoriteResult
)
data class FavoriteResult(
    val memories: List<Memories>
)
data class Memories(
    val id: Int,
    val imageUrl: String
)
