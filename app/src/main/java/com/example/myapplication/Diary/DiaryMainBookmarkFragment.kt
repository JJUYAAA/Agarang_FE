package com.example.myapplication.Diary

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.Data.Response.FavoriteResponse
import com.example.myapplication.R
import com.example.myapplication.Retrofit.FavoriteRetrofit
import com.example.myapplication.databinding.FragmentDiaryMainBookmarkBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DiaryMainBookmarkFragment : Fragment() {

    lateinit var binding: FragmentDiaryMainBookmarkBinding
    private var DiaryBookmarkAdapter : DiaryMainBookmarkAdapter?= null
    private var DiaryBookmarkitemList : ArrayList<DiaryMainDayData> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDiaryMainBookmarkBinding.inflate(inflater, container, false)

        initRecyclerView()

        fetchFavoriteData()

        return binding.root
    }

    private fun initRecyclerView() {
        val spanCount = 3 // 열의 수
        DiaryBookmarkAdapter = DiaryMainBookmarkAdapter(requireContext(), DiaryBookmarkitemList)
        binding.rvDiaryDay.adapter = DiaryBookmarkAdapter
        binding.rvDiaryDay.layoutManager = GridLayoutManager(context, spanCount)

        // 아이템 크기 고정
        binding.rvDiaryDay.addItemDecoration(SquareItemDecoration(spanCount))
    }

    private fun fetchFavoriteData() {
        FavoriteRetrofit.FavoriteService.getFavorite("favorite").enqueue(object : Callback<FavoriteResponse> {
            override fun onResponse(call: Call<FavoriteResponse>, response: Response<FavoriteResponse>) {
                if (response.isSuccessful) {
                    response.body()?.result?.memories?.let { memories ->
                        for (memory in memories) {
                            DiaryBookmarkitemList.add(DiaryMainDayData(imageUrl = memory.imageUrl, date = "2024/5/1"))
                        }
                        DiaryBookmarkAdapter?.notifyDataSetChanged()
                    }
                } else {
                    /*Log.e("DiaryMainBookmarkFragment", "Response error: ${response.errorBody()}")*/
                    // 오류 응답 처리
                    val errorBody = response.errorBody()?.string()
                    Log.e("DiaryMainBookmarkFragment", "Response error: $errorBody")
                }
            }

            override fun onFailure(call: Call<FavoriteResponse>, t: Throwable) {
                Log.e("DiaryMainBookmarkFragment", "API call failed: ${t.message}")
            }
        })
    }
}