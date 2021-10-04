package com.minchinovich.rsandroidtask5

import com.minchinovich.rsandroidtask5.data.entities.GalleryItem

interface SimpleNavigator {
    fun showPhoto(galleryItem: GalleryItem)
}