package com.minchinovich.rsandroidtask5.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.minchinovich.rsandroidtask5.App
import com.minchinovich.rsandroidtask5.data.dao.GalleryDao
import com.minchinovich.rsandroidtask5.data.entities.GalleryItem
import com.minchinovich.rsandroidtask5.data.galleryList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [GalleryItem::class], version = 1)
abstract class AppRoomDatabase : RoomDatabase(), AppDatabase{

    abstract override fun galleryDao(): GalleryDao

    companion object{
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AppRoomDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            val application = context.applicationContext as App
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    application,
                    AppRoomDatabase::class.java,
                    AppDatabase.NAME
                ).addCallback(PrepopulateDatabaseCallback(application.applicationScope)).build()
                INSTANCE = instance
                instance
            }
        }
    }

    // TODO delete before relise
    private class PrepopulateDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback(){

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database)
                }
            }
        }

        private suspend fun populateDatabase(roomDatabase: AppRoomDatabase) {
            roomDatabase.galleryDao().insertAll(galleryList())
        }
    }
}