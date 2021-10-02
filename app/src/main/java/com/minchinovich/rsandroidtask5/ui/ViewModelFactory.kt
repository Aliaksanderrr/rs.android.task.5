package com.minchinovich.rsandroidtask5.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.minchinovich.rsandroidtask5.domain.GalleryRepository
import com.minchinovich.rsandroidtask5.ui.main.PhotoGalleryViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory(
    private val galleryRepository: GalleryRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(PhotoGalleryViewModel::class.java)){
            return PhotoGalleryViewModel(galleryRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass::class.java}")
    }
}