package com.example.coverme.presentation.adaptor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.coverme.R
import com.example.coverme.data.remote.DTO.PhotoDTO.PhotoDTOItem
import com.example.coverme.presentation.screens.Home.PhotoDetails


class PhotoPagingAdaptor(
    private val onItemClick: (String) -> Unit
) : PagingDataAdapter<PhotoDTOItem, PhotoPagingAdaptor.PhotoViewHolder>(COMPARATOR) {

    inner class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val image = itemView.findViewById<ImageView>(R.id.imageView)
        fun bind(photo: PhotoDTOItem) {
            Glide.with(image).load(photo.urls.regular).into(image)

            itemView.setOnClickListener {
               onItemClick(photo.id)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): PhotoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_photo, parent, false)
        return PhotoViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: PhotoViewHolder, position: Int
    ) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    //comparing the old photos and avoid reputation
    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<PhotoDTOItem>() {
            override fun areItemsTheSame(oldItem: PhotoDTOItem, newItem: PhotoDTOItem): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: PhotoDTOItem, newItem: PhotoDTOItem): Boolean =
                oldItem == newItem
        }
    }
}