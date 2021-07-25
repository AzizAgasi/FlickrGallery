package com.techdot.flickrgallery.ui.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.techdot.flickrgallery.databinding.PhotoLoadStateFooterBinding

// Load state adapter to implement pagination in gallery fragment
class FlickrPhotoLoadStateAdapter(private val retry: () -> Unit)
    :LoadStateAdapter<FlickrPhotoLoadStateAdapter.LoadStateSearchViewHolder>(){

    override fun onBindViewHolder(
        holder: FlickrPhotoLoadStateAdapter.LoadStateSearchViewHolder,
        loadState: LoadState
    ) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): FlickrPhotoLoadStateAdapter.LoadStateSearchViewHolder {
        val binding = PhotoLoadStateFooterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return LoadStateSearchViewHolder(binding)
    }

    inner class LoadStateSearchViewHolder(private val binding: PhotoLoadStateFooterBinding)
        : RecyclerView.ViewHolder(binding.root) {
            init {
                binding.buttonRetry.setOnClickListener {
                    retry.invoke()
                }
            }

            fun bind(loadState: LoadState) {
                binding.apply {
                    progressBar.isVisible = loadState is LoadState.Loading
                    buttonRetry.isVisible = loadState is LoadState.Loading
                    textViewError.isVisible = loadState is LoadState.Loading
                }
            }
        }

}