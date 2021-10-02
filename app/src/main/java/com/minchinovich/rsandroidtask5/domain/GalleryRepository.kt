package com.minchinovich.rsandroidtask5.domain

import android.util.Log
import com.minchinovich.rsandroidtask5.data.entities.GalleryItem
import com.minchinovich.rsandroidtask5.domain.network.RepoSearchResponse
import com.minchinovich.rsandroidtask5.domain.network.RepoSearchResult
import com.minchinovich.rsandroidtask5.domain.network.TheCatServise
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import retrofit2.HttpException
import java.io.IOException

private const val TAG = "MYTAG"
private const val STARTING_PAGE_INDEX = 0

class GalleryRepository(private val service: TheCatServise) {

    private val inMemoryCache = mutableListOf<GalleryItem>()
    private val searchResults = MutableSharedFlow<RepoSearchResult>(replay = 1)
    private var lastRequestedPage = STARTING_PAGE_INDEX
    private var isRequestInProgress = false

    suspend fun getSearchResultStream(query: String): Flow<RepoSearchResult> {
        Log.d(TAG, "New query: $query")
        lastRequestedPage = 1
        inMemoryCache.clear()
        requestAndSaveData(query)

        return searchResults
    }

    suspend fun requestMore(query: String) {
        if (isRequestInProgress) return
        val successful = requestAndSaveData(query)
        if (successful) {
            lastRequestedPage++
        }
    }

    suspend fun retry(query: String) {
        if (isRequestInProgress) return
        requestAndSaveData(query)
    }

    private suspend fun requestAndSaveData(query: String): Boolean {
        isRequestInProgress = true
        var successful = false

        try {
            val serverApiResponse = service.getCats(lastRequestedPage, NETWORK_PAGE_SIZE)
            Log.d(TAG, "response $serverApiResponse")
            val response = RepoSearchResponse(serverApiResponse)
            val repos = response.items ?: emptyList()
            inMemoryCache.addAll(repos)
            val reposByName = reposByName(query)
            searchResults.emit(RepoSearchResult.Success(reposByName))
            successful = true
        } catch (exception: IOException) {
            searchResults.emit(RepoSearchResult.Error(exception))
        } catch (exception: HttpException) {
            searchResults.emit(RepoSearchResult.Error(exception))
        }
        isRequestInProgress = false
        return successful
    }

    private fun reposByName(query: String): List<GalleryItem> {
        return inMemoryCache
    }


//    suspend fun addGallery(gallery: GalleryItem) = withContext(Dispatchers.IO){
//        galleryDataSource.addGallery(gallery)
//    }
//
//    suspend fun updateGallery(gallery: GalleryItem) = withContext(Dispatchers.IO){
//        galleryDataSource.updateGallery(gallery)
//    }
//    suspend fun removeGallery(gallery: GalleryItem) = withContext(Dispatchers.IO){
//        galleryDataSource.removeGallery(gallery)
//    }
//    fun observeGalleryList(): Flow<List<GalleryItem>> = galleryDataSource.observeGalleryList()
//
////TODO this code worked before db created;(
////    private val catFetch = CatFetch()
////
//    fun getCats(){
//        val cats = catFetch.getCats()
//        Log.d("TAG", "CatRepository: ${cats.size}}")
//    }

    companion object {
        const val NETWORK_PAGE_SIZE = 30
    }


}