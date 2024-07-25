package com.example.myapplication.Diary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentDiaryMainMonthBinding

class DiaryMainMonthFragment : Fragment() {

    lateinit var binding: FragmentDiaryMainMonthBinding
    private var DiaryMonthAdapter : DiaryMonthAdapter?= null
    private var DiaryMonthitemList : ArrayList<DiaryMainDayData> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDiaryMainMonthBinding.inflate(inflater, container, false)


        //RecyclerView 생성
        initRecyclerView()

        return binding.root
    }

    private fun initRecyclerView() {
        val spanCount = 3 // 열의 수
        DiaryMonthAdapter = DiaryMonthAdapter(requireContext(), DiaryMonthitemList)
        binding.rvDiaryMonth.adapter = DiaryMonthAdapter
        binding.rvDiaryMonth.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)

    }



}