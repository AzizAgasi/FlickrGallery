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

    val photos = MutableLiveData<List<Photo>>()

    init {
        getPhotos(DEFAULT_QUERY)
    }

     fun getPhotos(query: String) {
        viewModelScope.launch {
            photos.postValue(repository.getPhotosQuery(query))
        }
    }

    companion object {
        private const val DEFAULT_QUERY = "cat"
    }
}