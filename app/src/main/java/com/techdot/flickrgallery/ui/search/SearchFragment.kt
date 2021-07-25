package com.techdot.flickrgallery.ui.search

import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.techdot.flickrgallery.R
import com.techdot.flickrgallery.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_search.view.*
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private val viewModel by viewModels<SearchViewModel>()
    private var _binding: FragmentSearchBinding? = null
    private lateinit var searchView: SearchView

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentSearchBinding.bind(view)

        binding.photoSearchView.layoutManager =
            LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false)

        viewModel.photos.observe(viewLifecycleOwner, Observer {
            binding.searchLoading.visibility = View.INVISIBLE
            binding.photoSearchView.adapter = FlickrPhotoSearchAdapter(it, requireContext())
        })

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)

        searchView = menu?.findItem(R.id.app_bar_search)?.actionView as SearchView

        searchView.queryHint = "Search for an image"

        if(searchView.query != null && searchView.query != "") {
            search()
        }

        return super.onCreateOptionsMenu(menu, inflater)
    }

    private fun search() {
        Observable.create<String> { emitter ->
            searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (query != "" && query != null) {
                        binding.searchLoading.visibility = View.VISIBLE
                        getPhotosFromSearch(query)
                        searchView.clearFocus()
                    } else {
                        searchView.clearFocus()
                        Snackbar.make(requireView(), "Please enter a text to be searched!", Snackbar.LENGTH_SHORT).show()
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (!emitter.isDisposed) {
                        emitter.onNext(newText)
                    }
                    return true
                }
            })
        }
            .debounce(1000, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    binding.searchLoading.visibility = View.VISIBLE
                    getPhotosFromSearch(it)
                },
                {
                    Log.e("ERROR: ", it.message!!)
                },
                {
                    Log.d("QUERY: ", "SUCCESSFUL")
                }
            )
    }

    private fun getPhotosFromSearch(query: String) {
        viewModel.getPhotos(query)
        viewModel.photos.observe(viewLifecycleOwner, { photos ->
            binding.photoSearchView.adapter = FlickrPhotoSearchAdapter(photos, requireContext())
        })
        binding.searchLoading.visibility = View.INVISIBLE
        binding.photoSearchView.adapter?.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}