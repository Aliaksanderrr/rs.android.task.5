package com.minchinovich.rsandroidtask5.domain.network

import android.util.Log
import com.minchinovich.rsandroidtask5.data.entities.GalleryItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class CatFetch {

    private val retrofit : Retrofit = Retrofit.Builder()
        .baseUrl("https://api.thecatapi.com/v1/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    private val catApi = retrofit.create(ServerAPI::class.java)


    fun getCats() : List<GalleryItem>{
        val listGalleryItem = mutableListOf<GalleryItem>()
        val call : Call<List<GalleryItem>> = catApi.getcats()
        call.enqueue(object : Callback<List<GalleryItem>>{
            override fun onResponse(
                call: Call<List<GalleryItem>>,
                response: Response<List<GalleryItem>>
            ) {
                if(response.isSuccessful){
                    Log.d("TAG", "response: ${response.body()?.size}")
                    Log.d("TAG", "response: ${response.body()?.get(0)}")
                    response.body()?.let {
                        for (cat in it){
                            listGalleryItem.add(cat)
                        }
                    }
                } else {
                    //for example 404
                    Log.d("TAG", "response: ${response.code()}")
                }

            }

            override fun onFailure(call: Call<List<GalleryItem>>, t: Throwable) {
                Log.d("TAG", "onFailure(): ${t.message}")
            }

        })
        return listGalleryItem
    }


}