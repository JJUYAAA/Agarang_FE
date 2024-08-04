package com.example.myapplication.Diary

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.Data.Response.BookmarkMemory
import com.example.myapplication.Data.Response.DiaryBookmarkResponse
import com.example.myapplication.Retrofit.DiaryIF
import com.example.myapplication.Retrofit.RetrofitService
import com.example.myapplication.databinding.FragmentDiaryMainBookmarkBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DiaryMainBookmarkFragment : Fragment() {

    private lateinit var binding: FragmentDiaryMainBookmarkBinding
    private var DiaryBookmarkAdapter: DiaryMainBookmarkAdapter? = null
    private var DiaryBookmarkitemList: ArrayList<DiaryMainBookmarkData> = arrayListOf()

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
        val service = RetrofitService.retrofit.create(DiaryIF::class.java)

        service.getBookmarkedMemories("bookmark").enqueue(object : Callback<DiaryBookmarkResponse> {
            override fun onResponse(call: Call<DiaryBookmarkResponse>, response: Response<DiaryBookmarkResponse>) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()

                    if (apiResponse != null && apiResponse.isSuccess) {
                        updateRecyclerView(apiResponse.result.memoires)
                    } else {
                        Log.e("문제", apiResponse?.message ?: "Unknown error")
                    }
                } else {
                    // 오류 응답 처리
                    val errorBody = response.errorBody()?.string()
                    Log.e("오류", "Response error: $errorBody")
                    updateRecyclerView(emptyList()) // 빈 리스트로 RecyclerView 업데이트
                }
            }

            override fun onFailure(call: Call<DiaryBookmarkResponse>, t: Throwable) {
                Log.d("실패", t.message.toString())
                updateRecyclerView(emptyList()) // 네트워크 오류 발생 시 빈 리스트로 RecyclerView 업데이트
            }
        })
    }

    private fun updateRecyclerView(memories: List<BookmarkMemory>) {
        DiaryBookmarkitemList.clear()
        if (memories != null) {
            DiaryBookmarkitemList.addAll(memories.map { memory ->
                DiaryMainBookmarkData(
                    id = memory.id,
                    imageUrl = memory.imageUrl
                )
            })
        }
        DiaryBookmarkAdapter?.notifyDataSetChanged()
    }
}
