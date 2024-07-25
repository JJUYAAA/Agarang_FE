package com.example.myapplication.Diary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentDiaryMainDayBinding

class DiaryMainDayFragment : Fragment() {

    private lateinit var binding: FragmentDiaryMainDayBinding
    private var diaryDayAdapter: DiaryDayAdapter? = null
    private var diaryDayItemList: ArrayList<DiaryMainDayData> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDiaryMainDayBinding.inflate(inflater, container, false)
        initRecyclerView()
        return binding.root
    }

    private fun initRecyclerView() {
        val spanCount = 3
        diaryDayAdapter = DiaryDayAdapter(requireContext(), diaryDayItemList)
        binding.rvDiaryDay.apply {
            adapter = diaryDayAdapter
            layoutManager = GridLayoutManager(context, spanCount)
            addItemDecoration(SquareItemDecoration(spanCount))
        }

        diaryDayAdapter?.setOnItemClickListener(object : DiaryDayAdapter.OnItemClickListener {
            override fun onItemClick(item: DiaryMainDayData) {
                navigateToDiaryMainCard(item)
            }
        })
    }

    private fun navigateToDiaryMainCard(item: DiaryMainDayData) {
        val fragment = DiaryMainCardFragment()
        val bundle = Bundle().apply {
            putSerializable("data", ArrayList(diaryDayItemList))
            putInt("position", diaryDayItemList.indexOf(item))
        }
        fragment.arguments = bundle
        parentFragmentManager.beginTransaction().replace(R.id.main_frm, fragment).addToBackStack(null)
            .commit()
    }


}