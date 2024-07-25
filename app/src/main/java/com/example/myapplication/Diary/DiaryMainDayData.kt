package com.example.myapplication.Diary

import java.io.Serializable


data class DiaryMainDayData(
    val imageResId: Int? = null,
    val imageUrl: String? = null,
    val date: String,
    var bookmark: Int = 0,
    val content: String = "오늘 엄마랑 대관령에 다녀왔단다.오똑숲에서 많은 나무와 아름다운 할미 꽃을 보며 정말 행복했어. 자연 속에서 느낀 피톤치드 덕분에 마음이 한결 편안해졌단다. 우리 아가도 나중에 꼭 같이 가보자. 사랑해. 우리 아가!"
) : Serializable {
    val year: Int
    val month: Int
    val day: Int

    init {
        val parts = date.split("/").map { it.trim().toInt() }
        year = parts[0]
        month = parts[1]
        day = parts[2]
    }

    constructor(imageUrl: String, date: String) : this(imageResId = null, imageUrl = imageUrl, date = date)
}