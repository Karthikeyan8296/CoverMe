package com.example.coverme.presentation.adaptor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.coverme.R
import com.example.coverme.domain.models.PhotoModel


class PhotoPagingAdaptor(
    private val onItemClick: (String) -> Unit
) : PagingDataAdapter<PhotoModel, PhotoPagingAdaptor.PhotoViewHolder>(COMPARATOR) {

    //ViewHolder === One Row - this represents one item, in the row
    //purpose - Avoid repeated findViewById<>
    inner class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val image = itemView.findViewById<ImageView>(R.id.imageView)
        fun bind(photo: PhotoModel) {
            //load returns the requestBuilder
            Glide.with(itemView.context).load(photo.urls.regular).into(image)
            itemView.setOnClickListener {
                onItemClick(photo.id)
            }
        }
    }

    //IMPLEMENTED CLASS//
    //When u need a new row, use the MyViewHolder row
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): PhotoViewHolder {

        //View - TextView, Button etc..
        //ViewGroup - LinearLayout, FrameLayout etc..

        //LayoutInflater - It converts the XML resource into actual View objects, this process is called inflation
        //attachToRoot = true, When we need to manually adding the view by ourself.
        //attachToRoot = false, Some system component will attach it for you, in case here is RecycleView

        //If we are not inside the fragment manager or RecycleView, we use True, as we manually need to put!
        //Root = LayoutGroup, that is LinearLayout, FrameLayout

        //inflate main role -> create View Objects (false), or insert them into the parent! (true)
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_photo, parent, false)
        return PhotoViewHolder(view)
    }

    //For this row, put the correct data
    //Binds data to the view
    override fun onBindViewHolder(
        holder: PhotoViewHolder, position: Int
    ) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    //comparing the old photos and avoid reputation

    //DiffUtil is a utility class that calculates the difference between two lists
    // and outputs a list of update operations that converts the first list into the second one.
    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<PhotoModel>() {
            override fun areItemsTheSame(oldItem: PhotoModel, newItem: PhotoModel): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: PhotoModel, newItem: PhotoModel): Boolean =
                oldItem == newItem
        }
    }
}