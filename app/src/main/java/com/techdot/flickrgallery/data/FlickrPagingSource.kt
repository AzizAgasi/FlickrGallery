package com.techdot.flickrgallery.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.techdot.flickrgallery.api.FlickrApi
import com.techdot.flickrgallery.models.Photo
import dagger.Provides
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Named

private const val STARTING_PAGE = 1

class FlickrPagingSource(@Named("provideFlickrApi")private val api: FlickrApi): PagingSource<Int, Photo>() {
    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        val position = params.key?: STARTING_PAGE

        return try {
            val response = api.getPhotos(
                "flickr.photos.getRecent",
                20,
                position,
                "6f102c62f41998d151e5a1b48713cf13",
                "json",
                1,
                "url_s"
            )
            val photos = response.body()?.photos

            LoadResult.Page(
                photos!!.photo,
                prevKey = if (position == STARTING_PAGE) null else position -1,
                nextKey = if (photos.photo.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}