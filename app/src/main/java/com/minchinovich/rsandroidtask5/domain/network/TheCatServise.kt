package com.minchinovich.rsandroidtask5.domain.network

import com.minchinovich.rsandroidtask5.data.entities.GalleryItem
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface TheCatServise {

    @GET("images/search")
    suspend fun getCats(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): List<GalleryItem>


    companion object{
        private const val BASE_URL = "https://api.thecatapi.com/v1/"

        fun create(): TheCatServise {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(TheCatServise::class.java)
        }
    }
}