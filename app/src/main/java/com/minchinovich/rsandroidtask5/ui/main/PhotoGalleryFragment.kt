package com.minchinovich.rsandroidtask5.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.minchinovich.rsandroidtask5.R
import com.minchinovich.rsandroidtask5.databinding.PhotoGalleryFragmentBinding
import com.minchinovich.rsandroidtask5.utils.requireApp
import com.minchinovich.rsandroidtask5.utils.requireMainActivity

private const val SPAN_COUNT = 2

class PhotoGalleryFragment : Fragment() {

    private val viewModel: PhotoGalleryViewModel by viewModels {
        requireApp().viewModelFactory
    }
    private var _binding: PhotoGalleryFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PhotoGalleryFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireMainActivity().supportActionBar?.apply {
            title = getString(R.string.photo_gallery_fragment_title)
        }

        val galleryAdapter = PhotoAdapter(
            onClick = { photo ->
                //TODO Create on click to photo
                Toast.makeText(context, "${photo.hashCode()} pressed", Toast.LENGTH_SHORT).show()
            }
        )
        binding.photoRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, SPAN_COUNT)
            adapter = galleryAdapter

        }
        viewModel.galleryLiveData.observe(viewLifecycleOwner){galleries ->
            galleryAdapter.submitList(galleries)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        fun newInstance() = PhotoGalleryFragment()
    }
}