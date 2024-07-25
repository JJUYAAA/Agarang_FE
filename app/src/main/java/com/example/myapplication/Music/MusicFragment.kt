package com.example.myapplication.Music

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentMusicBinding

class MusicFragment : Fragment() {
    lateinit var binding:FragmentMusicBinding
    private var itemList:ArrayList<MusicMainData> = arrayListOf()
    private var musicMainAdapter:MusicMainAdapter?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentMusicBinding.inflate(inflater,container,false)

        itemList.add(MusicMainData(R.drawable.music_sample,"즐겨 찾기"))
        itemList.add(MusicMainData(R.drawable.music_sample,"운동할 때 들을 플레이리스트"))
        itemList.add(MusicMainData(R.drawable.music_sample,"플레이리스트"))

        recycler()
        return binding.root
    }

    private fun recycler() {
        musicMainAdapter=MusicMainAdapter(itemList,requireActivity())
        binding.rvMusic.adapter=musicMainAdapter
        val spacingVertical=resources.getDimensionPixelSize(R.dimen.item_spacing)
        val spanCount=2
        val spacingGrid = resources.getDimensionPixelSize(R.dimen.music_grid_spacing)
        val includeEdge=true
        binding.rvMusic.layoutManager=GridLayoutManager(requireContext(),spanCount)
//        binding.rvMusic.addItemDecoration(musicMainAdapter!!.MusicMainItemDecoration(spacingVertical))
//        binding.rvMusic.addItemDecoration(musicMainAdapter!!.GridSpacingItemDecoration(spanCount,spacingGrid,includeEdge))
    }

}