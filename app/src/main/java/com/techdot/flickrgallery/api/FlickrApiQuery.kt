package com.techdot.flickrgallery.api

import com.techdot.flickrgallery.Constants
import com.techdot.flickrgallery.models.PhotosResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrApiQuery {
    @GET("rest/?")
    suspend fun getPhotoQuery(
        @Query(Constants.METHOD) method: String,
        @Query(Constants.KEY) key: String,
        @Query(Constants.FORMAT) format: String,
        @Query(Constants.NOJSO) nojsoCallback: Int,
        @Query(Constants.EXTRA) extras: String,
        @Query(Constants.TEXT) text: String
    ): Response<PhotosResponse>
}