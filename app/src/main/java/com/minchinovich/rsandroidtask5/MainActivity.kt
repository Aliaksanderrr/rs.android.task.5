package com.minchinovich.rsandroidtask5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.minchinovich.rsandroidtask5.databinding.ActivityMainBinding
import com.minchinovich.rsandroidtask5.ui.main.PhotoGalleryFragment

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

    //TODO here will be add override fun by SimpleNavigator
}