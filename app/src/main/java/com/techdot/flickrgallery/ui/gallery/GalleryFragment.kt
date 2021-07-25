package com.techdot.flickrgallery.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import com.google.android.material.snackbar.Snackbar
import com.techdot.flickrgallery.R
import com.techdot.flickrgallery.databinding.FragmentGalleryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GalleryFragment : Fragment(R.layout.fragment_gallery) {

    private val viewModel by viewModels<GalleryViewModel>()
    private var _binding: FragmentGalleryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentGalleryBinding.bind(view)

        val adapter = FlickrPhotoAdapter()

        binding.apply {
            recyclerView.apply {
                setHasFixedSize(true)
                itemAnimator = null
                setAdapter(adapter.withLoadStateHeaderAndFooter(
                    header = FlickrPhotoLoadStateAdapter {adapter.retry()},
                    footer = FlickrPhotoLoadStateAdapter {adapter.retry()}
                ))
            }
        }

        viewModel.photos.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        adapter.addLoadStateListener { loadState ->
            binding.apply {
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                recyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading

                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    adapter.itemCount < 1) {
                    recyclerView.isVisible = false
                }
                if (loadState.source.refresh is LoadState.Error) {
                    Snackbar.make(view, "Network Error", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Retry", View.OnClickListener {
                            adapter.retry()
                        })
                        .show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}