package com.minchinovich.rsandroidtask5.domain.datasources

import com.minchinovich.rsandroidtask5.data.dao.GalleryDao
import com.minchinovich.rsandroidtask5.data.entities.GalleryItem
import kotlinx.coroutines.flow.Flow

class GalleryDataSource (private val galleryDao: GalleryDao) {

    suspend fun addGallery(gallery: GalleryItem) = galleryDao.insert(gallery)

    suspend fun updateGallery(gallery: GalleryItem) = galleryDao.update(gallery)

    suspend fun removeGallery(gallery: GalleryItem) = galleryDao.remove(gallery)

    fun observeGalleryList(): Flow<List<GalleryItem>> = galleryDao.observeAnimalList()
}