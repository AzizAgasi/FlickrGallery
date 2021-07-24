package com.techdot.flickrgallery.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.techdot.flickrgallery.api.FlickrApiQuery
import com.techdot.flickrgallery.models.Photo
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhotoRepositoryQuery @Inject constructor(private val api: FlickrApiQuery) {
    suspend fun getPhotosQuery(query: String): List<Photo> {
        if (query != null && query != "") {
            val response = api.getPhotoQuery(
                "flickr.photos.search",
                "6f102c62f41998d151e5a1b48713cf13",
                "json",
                1,
                "url_s",
                query
            )
            return response.body()?.photos!!.photo
        }
        return emptyList()
    }
}