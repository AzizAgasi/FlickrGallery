package com.techdot.flickrgallery.ui.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.techdot.flickrgallery.databinding.ItemPhotoBinding
import com.techdot.flickrgallery.models.Photo

class FlickrPhotoAdapter
    : PagingDataAdapter<Photo, FlickrPhotoAdapter.ViewHolder>(PHOTO_COMPARATOR){

    override fun onBindViewHolder(holder: FlickrPhotoAdapter.ViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FlickrPhotoAdapter.ViewHolder {
        val binding =
            ItemPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    inner class ViewHolder(private val binding: ItemPhotoBinding):
        RecyclerView.ViewHolder(binding.root) {
            fun bind(photo: Photo) {
                Glide.with(itemView)
                    .load(photo.url_s)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(binding.imageView)

                binding.textViewUserName.text = photo.title
            }
        }

    companion object {
        private val PHOTO_COMPARATOR = object : DiffUtil.ItemCallback<Photo>() {
            override fun areItemsTheSame(oldItem: Photo, newItem: Photo) =
                oldItem.title == newItem.title

            override fun areContentsTheSame(oldItem: Photo, newItem: Photo) =
                oldItem == newItem
        }
    }
}