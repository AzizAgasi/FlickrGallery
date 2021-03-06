package com.techdot.flickrgallery.ui.search

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.techdot.flickrgallery.databinding.ItemPhotoBinding
import com.techdot.flickrgallery.models.Photo
import com.techdot.flickrgallery.ui.gallery.FlickrPhotoAdapter

// Adapter for search fragment without pagination
class FlickrPhotoSearchAdapter(photoList: List<Photo>, context: Context)
    :RecyclerView.Adapter<FlickrPhotoSearchAdapter.ViewHolder>() {

    private val photos = photoList
    private val context = context

    inner class ViewHolder(private val binding: ItemPhotoBinding, private val context: Context):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: Photo) {
            if(photo.url_s != null) {
                Glide.with(context)
                    .load(Uri.parse(photo.url_s))
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(binding.imageView)

                binding.textViewUserName.text = photo.owner
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPhotoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val photo = photos[position]
        holder.bind(photo)
    }

    override fun getItemCount(): Int {
        return photos.size
    }
}