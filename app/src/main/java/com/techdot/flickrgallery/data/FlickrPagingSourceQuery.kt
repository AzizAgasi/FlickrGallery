package com.techdot.flickrgallery.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.techdot.flickrgallery.api.FlickrApi
import com.techdot.flickrgallery.api.FlickrApiQuery
import com.techdot.flickrgallery.models.Photo
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Named

//private const val STARTING_PAGE = 1
//
//class FlickrPagingSourceQuery(@Named("provideFlickrApiQuery")private val api: FlickrApiQuery, private val query: String):
//    PagingSource<Int, Photo>() {
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
//        val position = params.key?: STARTING_PAGE
//
//        return try {
//            val response = api.getPhotoQuery(
//                "flickr.photos.search",
//                position,
//                "6f102c62f41998d151e5a1b48713cf13",
//                "json",
//                1,
//                "url_s",
//                query
//            )
//            val photos = response.body()?.photos
//
//
//            LoadResult.Page(
//                photos!!.photo,
//                prevKey = if (position == STARTING_PAGE) null else position -1,
//                nextKey = if (photos.photo.isEmpty()) null else position + 1
//            )
//        } catch (exception: IOException) {
//            LoadResult.Error(exception)
//        } catch (exception: HttpException) {
//            LoadResult.Error(exception)
//        }
//    }
//
//    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
//        TODO("Not yet implemented")
//    }
//}