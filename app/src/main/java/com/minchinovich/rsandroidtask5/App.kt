package com.minchinovich.rsandroidtask5

import android.app.Application
import com.minchinovich.rsandroidtask5.data.db.AppRoomDatabase
import com.minchinovich.rsandroidtask5.domain.GalleryRepository
import com.minchinovich.rsandroidtask5.domain.datasources.GalleryDataSource
import com.minchinovich.rsandroidtask5.domain.network.TheCatServise
import com.minchinovich.rsandroidtask5.ui.ViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

//only for code-author!!!
//from email: midas1989@mail.ru
//key: 15c1b9ac-7f30-4b61-93c7-012350793ad3
//letter body:
//Use it as the 'x-api-key' header when making any request to the API, or by adding as a query
//string parameter e.g. 'api_key=15c1b9ac-7f30-4b61-93c7-012350793ad3'


class App: Application() {

    // No need to cancel this scope as it'll be torn down with the process
    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy {
        AppRoomDatabase.getDatabase(this)
    }
//    val dataSource by lazy { GalleryDataSource(database.galleryDao()) }
    val theCatServise by lazy { TheCatServise.create() }
    val galleryRepository by lazy { GalleryRepository(theCatServise) }

    val viewModelFactory by lazy {
        ViewModelFactory(
            galleryRepository = galleryRepository
        )
    }
}