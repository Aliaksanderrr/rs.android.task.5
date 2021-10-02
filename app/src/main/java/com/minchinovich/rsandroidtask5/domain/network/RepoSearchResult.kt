package com.minchinovich.rsandroidtask5.domain.network

import com.minchinovich.rsandroidtask5.data.entities.GalleryItem
import java.lang.Exception

sealed class RepoSearchResult {
    data class Success(val data: List<GalleryItem>) : RepoSearchResult()
    data class Error(val error: Exception) : RepoSearchResult()
}