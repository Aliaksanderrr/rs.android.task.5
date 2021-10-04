package com.minchinovich.rsandroidtask5.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.minchinovich.rsandroidtask5.App
import com.minchinovich.rsandroidtask5.data.dao.GalleryDao
import com.minchinovich.rsandroidtask5.data.entities.GalleryItem

@Database(entities = [GalleryItem::class], version = 1)
abstract class AppRoomDatabase : RoomDatabase(), AppDatabase{

    abstract override fun galleryDao(): GalleryDao

    companion object{
        @Volatile
        private var INSTANCE: AppRoomDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val application = context.applicationContext as App
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    application,
                    AppRoomDatabase::class.java,
                    AppDatabase.NAME
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
