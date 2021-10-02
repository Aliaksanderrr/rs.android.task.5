package com.minchinovich.rsandroidtask5.data.dao

import androidx.room.*
import com.minchinovich.rsandroidtask5.data.entities.GalleryItem
import kotlinx.coroutines.flow.Flow
import retrofit2.http.DELETE

@Dao
interface GalleryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(galleryItem: GalleryItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(galleryItems: List<GalleryItem>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(galleryItem: GalleryItem)

    @Delete
    suspend fun remove(galleryItem: GalleryItem)

    @Query("SELECT * FROM gallery")
    fun observeAnimalList(): Flow<List<GalleryItem>>
}