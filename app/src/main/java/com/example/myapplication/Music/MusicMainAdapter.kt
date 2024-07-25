package com.example.myapplication.Music

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.example.myapplication.Diary.DiaryDayAdapter
import com.example.myapplication.R
import com.example.myapplication.databinding.MusicItemBinding

class MusicMainAdapter(val items:ArrayList<MusicMainData>, private val fragmentActivity: FragmentActivity):RecyclerView.Adapter<MusicMainAdapter.ViewHolder>() {
    private lateinit var itemClickListener:DiaryDayAdapter.OnItemClickListener
    inner class ViewHolder (val binding:MusicItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(item: MusicMainData){
            binding.ivRvCover1.setImageResource(item.musicImgId)
            binding.tvCover1.text=item.musicContent

            if (position==0){
                binding.ivCoverHeart.visibility= View.VISIBLE
            }else{
                binding.ivCoverHeart.visibility= View.GONE
            }

            binding.ivRvCover1.setOnClickListener{
                val albumTitle = binding.tvCover1.text.toString()
                openMusicAlbumFragment(albumTitle)
            }
        }
        private fun openMusicAlbumFragment(albumTitle: String) {
            val bundle = Bundle()
            bundle.putString("albumTitle", albumTitle)

            val musicAlbumFragment = MusicAlbumFragment()
            musicAlbumFragment.arguments = bundle

            val fragmentManager: FragmentManager = fragmentActivity.supportFragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.container, musicAlbumFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
    }

    inner class MusicMainItemDecoration(private val height : Int) : ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            if(parent.getChildAdapterPosition(view)!=parent.adapter!!.itemCount-1){
                outRect.bottom=height
            }
        }
    }

    inner class GridSpacingItemDecoration (private val spanCount: Int, private val spacing: Int, private val includeEdge: Boolean):ItemDecoration(){
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            val position = parent.getChildAdapterPosition(view) // item position
            val column = position % spanCount // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount
                outRect.right = (column + 1) * spacing / spanCount

                if (position < spanCount) { // top edge
                    outRect.top = spacing
                }
                outRect.bottom = spacing // item bottom
            } else {
                outRect.left = column * spacing / spanCount
                outRect.right = spacing - (column + 1) * spacing / spanCount
                if (position >= spanCount) {
                    outRect.top = spacing // item top
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicMainAdapter.ViewHolder {
        val binding=MusicItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

}