package com.techdot.flickrgallery.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.techdot.flickrgallery.api.FlickrApi
import com.techdot.flickrgallery.api.FlickrApiQuery
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhotoRepository @Inject constructor(private val api: FlickrApi) {
    fun getPhotos() = Pager(
        config = PagingConfig(
            pageSize = 3,
            maxSize = 100,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {FlickrPagingSource(api)}
    ).liveData
}