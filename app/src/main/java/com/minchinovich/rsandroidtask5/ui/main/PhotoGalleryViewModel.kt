package com.minchinovich.rsandroidtask5.ui.main

import androidx.lifecycle.*
import com.minchinovich.rsandroidtask5.data.entities.GalleryItem
import com.minchinovich.rsandroidtask5.domain.GalleryRepository
import com.minchinovich.rsandroidtask5.domain.network.RepoSearchResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class PhotoGalleryViewModel(
    private val repository: GalleryRepository
) : ViewModel() {

    val state: LiveData<UiState>

    /**
     * Processor of side effects from the UI which in turn feedback into [state]
     */
    val accept: (UiAction) -> Unit

    init {
        val queryLiveData =
            MutableLiveData(DEFAULT_QUERY)

        state = queryLiveData
            .distinctUntilChanged()
            .switchMap { queryString ->
                liveData {
                    val uiState = repository.getSearchResultStream(queryString)
                        .map {
                            UiState(
                                query = queryString,
                                searchResult = it
                            )
                        }
                        .asLiveData(Dispatchers.Main)
                    emitSource(uiState)
                }
            }

        accept = { action ->
            when (action) {
                is UiAction.Search -> queryLiveData.postValue(action.query)
                is UiAction.Scroll -> if (action.shouldFetchMore) {
                    val immutableQuery = queryLiveData.value
                    if (immutableQuery != null) {
                        viewModelScope.launch {
                            repository.requestMore(immutableQuery)
                        }
                    }
                }
            }
        }
    }

}

private val UiAction.Scroll.shouldFetchMore
    get() = visibleItemCount + lastVisibleItemPosition + VISIBLE_THRESHOLD >= totalItemCount

sealed class UiAction {
    data class Search(val query: String) : UiAction()
    data class Scroll(
        val visibleItemCount: Int,
        val lastVisibleItemPosition: Int,
        val totalItemCount: Int
    ) : UiAction()
}

data class UiState(
    val query: String,
    val searchResult: RepoSearchResult
)

private const val VISIBLE_THRESHOLD = 5
private const val LAST_SEARCH_QUERY: String = "last_search_query"
private const val DEFAULT_QUERY = "Android"