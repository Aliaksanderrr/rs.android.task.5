package com.minchinovich.rsandroidtask5.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = GalleryItem.TABLE_NAME)
data class GalleryItem(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = ITEM_ID_PROPERTY) var itemId: String = "",
    @ColumnInfo(name = HEIGHT_PROPERTY) var height: Int = 0,
    @ColumnInfo(name = WIGHT_PROPERTY) var width: Int = 0,
    @ColumnInfo(name = URL_PROPERTY) var url: String = ""){

    companion object{
        const val TABLE_NAME = "gallery"
        const val ITEM_ID_PROPERTY = "item_id"
        const val HEIGHT_PROPERTY = "height"
        const val WIGHT_PROPERTY = "wight"
        const val URL_PROPERTY = "url"
    }
}




//    var title: String = "",
//    var id: String = "",
//    @SerializedName("url_s") var url: String = "",
//    @SerializedName("owner") var owner: String = ""
//) {
//    val photoPageUri: Uri
//        get() {
//            return Uri.parse("https://www.flickr.com/photos/")
//                .buildUpon()
//                .appendPath(owner)
//                .appendPath(id)
//                .build()
//        }
//}