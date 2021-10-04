package com.minchinovich.rsandroidtask5.ui.main

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.minchinovich.rsandroidtask5.R
import com.minchinovich.rsandroidtask5.data.entities.GalleryItem
import com.minchinovich.rsandroidtask5.databinding.PhotoGalleryPhotoItemBinding

typealias OnAnimalClick = (GalleryItem) -> Unit

class PhotoAdapter(private val onClick: OnAnimalClick) :
    ListAdapter<GalleryItem, PhotoAdapter.PhotoViewHolder>(PhotoDiffCallback){

    class PhotoViewHolder(
            private val itemBinding: PhotoGalleryPhotoItemBinding,
            onClick: OnAnimalClick
    ) : RecyclerView.ViewHolder(itemBinding.root){

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
                itemBinding.imageCard.load(photo.url){
                    placeholder(R.drawable.ic_placeholder_24)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val itemBinding = PhotoGalleryPhotoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        Log.d("TAG", "onCreateViewHolder() ")
        return PhotoViewHolder(itemBinding, onClick)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val repoItem = getItem(position)
        if (repoItem != null) {
            holder.bind(repoItem)
        }
    }

}

private object PhotoDiffCallback : DiffUtil.ItemCallback<GalleryItem>() {
    override fun areItemsTheSame(oldItem: GalleryItem, newItem: GalleryItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: GalleryItem, newItem: GalleryItem): Boolean {
        return oldItem == newItem
    }
}
