package com.techdot.flickrgallery.ui.search

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.techdot.flickrgallery.data.PhotoRepositoryQuery
import com.techdot.flickrgallery.models.Photo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: PhotoRepositoryQuery) :
    ViewModel() {

    private val currentQuery = MutableLiveData(DEFAULT_QUERY)
    val photos = MutableLiveData<List<Photo>>()

    init {
        getPhotos()
    }

    private fun getPhotos() {
        viewModelScope.launch {
            photos.postValue(repository.getPhotosQuery(DEFAULT_QUERY))
        }
    }

    companion object {
        private const val DEFAULT_QUERY = "cat"
    }
}