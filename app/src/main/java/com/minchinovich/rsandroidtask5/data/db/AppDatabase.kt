package com.minchinovich.rsandroidtask5.data.db

import com.minchinovich.rsandroidtask5.data.dao.GalleryDao

interface AppDatabase {

    companion object{
        const val NAME = "gallery_database"
    }

    fun galleryDao(): GalleryDao
}