package com.example.myapplication.Diary

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.GridBookmarkItemBinding
import com.example.myapplication.databinding.GridDayItemBinding

class DiaryMainBookmarkAdapter (val context: Context, val items: ArrayList<DiaryMainDayData>) : RecyclerView.Adapter<DiaryMainBookmarkAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: GridBookmarkItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item : DiaryMainDayData){

            /*Glide.with(context)
                .load(item.imageResId)
                .centerCrop()
                .into(binding.ivDay)*/

            // 이미지 로드
            if (item.imageUrl != null) {
                Glide.with(context).load(item.imageUrl).into(binding.ivDay)
            } else if (item.imageResId != null) {
                binding.ivDay.setImageResource(item.imageResId)
            }
            /*// 날짜 설정
            binding.textViewDate.text = item.date*/
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = GridBookmarkItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

}