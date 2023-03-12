package com.deven.carouselrecyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.deven.carouselrecyclerview.model.ModelImages
import com.deven.recyclerviewinfinitescrioll.databinding.ItemBinding

class ImageAdapter(private val context: Context, private val mList: List<ModelImages>) :
    RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(mList[position]){
                Glide
                    .with(context)
                    .load(url)
                    .centerCrop()
                    .into(binding.imageView);
            }
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    inner class ViewHolder(val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root)
}