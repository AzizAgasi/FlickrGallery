package com.techdot.flickrgallery.api

import com.techdot.flickrgallery.Constants.Companion.EXTRA
import com.techdot.flickrgallery.Constants.Companion.FORMAT
import com.techdot.flickrgallery.Constants.Companion.KEY
import com.techdot.flickrgallery.Constants.Companion.METHOD
import com.techdot.flickrgallery.Constants.Companion.NOJSO
import com.techdot.flickrgallery.Constants.Companion.PAGE
import com.techdot.flickrgallery.Constants.Companion.PER_PAGE
import com.techdot.flickrgallery.Constants.Companion.TEXT
import com.techdot.flickrgallery.models.PhotosResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrApi {
    // function to get all photos from Flickr Api
    @GET("rest/?")
    suspend fun getPhotos(
        @Query(METHOD) method: String,
        @Query(PER_PAGE) perPage: Int,
        @Query(PAGE) page: Int,
        @Query(KEY) key: String,
        @Query(FORMAT) format: String,
        @Query(NOJSO) nojsoCallback: Int,
        @Query(EXTRA) extras: String
    ): Response<PhotosResponse>

//    @GET("rest/?")
//    suspend fun getPhotoQuery(
//        @Query(METHOD) method: String,
//        @Query(PAGE) page: Int,
//        @Query(KEY) key: String,
//        @Query(FORMAT) format: String,
//        @Query(NOJSO) nojsoCallback: Int,
//        @Query(EXTRA) extras: String,
//        @Query(TEXT) text: String
//    ): Response<PhotosResponse>
}