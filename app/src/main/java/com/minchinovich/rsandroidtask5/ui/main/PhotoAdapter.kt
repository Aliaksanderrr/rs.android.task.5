package com.minchinovich.rsandroidtask5.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.minchinovich.rsandroidtask5.data.entities.GalleryItem
import com.minchinovich.rsandroidtask5.databinding.PhotoGalleryPhotoItemBinding

typealias OnAnimalClick = (GalleryItem) -> Unit

class PhotoAdapter(private val onClick: OnAnimalClick) :
    ListAdapter<GalleryItem, PhotoAdapter.PhotoViewHolder>(PhotoDiffCallback) {

        class PhotoViewHolder(
            private val itemBinding: PhotoGalleryPhotoItemBinding,
            onClick: OnAnimalClick
        ) :
            RecyclerView.ViewHolder(itemBinding.root){

            private var currentPhoto: GalleryItem? = null

            init {
                itemBinding.imageCard.apply {
                    setOnClickListener {
                        currentPhoto?.let {
                            onClick(it)
                        }
                    }
                }
            }

            fun bind(photo: GalleryItem){
                currentPhoto = photo
                itemBinding.imageCard.apply {
//                    text = photo.itemId
                //TODO ??? itemBinding.imageCard.load()
                    itemBinding.imageCard.load(photo.url)
                }

            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val itemBinding = PhotoGalleryPhotoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoViewHolder(itemBinding, onClick)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val galleryItem = getItem(position)
        holder.bind(galleryItem)
    }

}

object PhotoDiffCallback : DiffUtil.ItemCallback<GalleryItem>() {
    override fun areItemsTheSame(oldItem: GalleryItem, newItem: GalleryItem): Boolean {
        return oldItem.itemId == newItem.itemId
    }

    override fun areContentsTheSame(oldItem: GalleryItem, newItem: GalleryItem): Boolean {
        return oldItem == newItem
    }
}