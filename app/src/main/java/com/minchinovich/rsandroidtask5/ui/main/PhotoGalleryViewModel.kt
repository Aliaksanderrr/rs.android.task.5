package com.minchinovich.rsandroidtask5.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.minchinovich.rsandroidtask5.data.entities.GalleryItem
import com.minchinovich.rsandroidtask5.domain.GalleryRepository
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class PhotoGalleryViewModel(
    private val galleryRepository: GalleryRepository
) : ViewModel() {

    val galleryLiveData: LiveData<List<GalleryItem>> =
        galleryRepository.observeGalleryList().asLiveData()

    fun remove(galleryItem: GalleryItem) {
        viewModelScope.launch {
            kotlin.runCatching {
                galleryRepository.removeGallery(galleryItem)
            }
        }
    }
}