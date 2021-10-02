package com.minchinovich.rsandroidtask5.domain.network

import com.google.gson.annotations.SerializedName
import com.minchinovich.rsandroidtask5.data.entities.GalleryItem


data class RepoSearchResponse (
//    @SerializedName("total_count") val total: Int = 0,
//    @SerializedName("items") val items: List<GalleryItem> = emptyList(),
    val items: List<GalleryItem> = emptyList(),
    val nextPage: Int? = null
)

