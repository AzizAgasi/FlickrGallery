package com.techdot.flickrgallery.ui.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.techdot.flickrgallery.data.PhotoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(private val repository: PhotoRepository) :
    ViewModel() {

        val photos = repository.getPhotos().cachedIn(viewModelScope)
}