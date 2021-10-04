package com.minchinovich.rsandroidtask5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.minchinovich.rsandroidtask5.data.entities.GalleryItem
import com.minchinovich.rsandroidtask5.databinding.ActivityMainBinding
import com.minchinovich.rsandroidtask5.ui.main.PhotoGalleryFragment
import com.minchinovich.rsandroidtask5.ui.photo.PhotoFragment

class MainActivity : AppCompatActivity(), SimpleNavigator {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, PhotoGalleryFragment.newInstance())
                .commit()
        }
    }

    override fun showPhoto(galleryItem: GalleryItem) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, PhotoFragment.newInstance(galleryItem))
            .addToBackStack(null)
            .commit()
    }
}