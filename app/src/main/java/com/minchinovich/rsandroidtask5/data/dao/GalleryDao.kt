package com.minchinovich.rsandroidtask5.data.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.minchinovich.rsandroidtask5.data.entities.GalleryItem
import kotlinx.coroutines.flow.Flow

@Dao
interface GalleryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(galleryItem: GalleryItem)

    //needed for Pager
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(galleryItems: List<GalleryItem>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(galleryItem: GalleryItem)

    @Delete
    suspend fun remove(galleryItem: GalleryItem)

    //needed for Pager
    @Query("DELETE FROM gallery")
    suspend fun clearAll()

    @Query("SELECT * FROM gallery")
    fun observeAnimalList(): Flow<List<GalleryItem>>

    //needed for Pager
    @Query("SELECT * FROM gallery WHERE item_id LIKE :query")
    fun pagingSource(query: String) : PagingSource<Int, GalleryItem>
}
