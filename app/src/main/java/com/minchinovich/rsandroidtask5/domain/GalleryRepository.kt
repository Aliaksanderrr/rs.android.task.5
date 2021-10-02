package com.minchinovich.rsandroidtask5.domain

import com.minchinovich.rsandroidtask5.data.entities.GalleryItem
import com.minchinovich.rsandroidtask5.domain.datasources.GalleryDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class GalleryRepository(private val galleryDataSource: GalleryDataSource) {

    suspend fun addGallery(gallery: GalleryItem) = withContext(Dispatchers.IO){
        galleryDataSource.addGallery(gallery)
    }

    suspend fun updateGallery(gallery: GalleryItem) = withContext(Dispatchers.IO){
        galleryDataSource.updateGallery(gallery)
    }
    suspend fun removeGallery(gallery: GalleryItem) = withContext(Dispatchers.IO){
        galleryDataSource.removeGallery(gallery)
    }
    fun observeGalleryList(): Flow<List<GalleryItem>> = galleryDataSource.observeGalleryList()

//TODO this code worked before db created;(
//    private val catFetch = CatFetch()
//
//    fun getCats(){
//        val cats = catFetch.getCats()
//        Log.d("TAG", "CatRepository: ${cats.size}}")
//    }


}