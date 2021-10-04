package com.minchinovich.rsandroidtask5.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.map
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.minchinovich.rsandroidtask5.R
import com.minchinovich.rsandroidtask5.databinding.PhotoGalleryFragmentBinding
import com.minchinovich.rsandroidtask5.domain.network.RepoSearchResult
import com.minchinovich.rsandroidtask5.utils.navigator
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
        // add dividers between RecyclerView's row items
        val decoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        binding.photoRecyclerView.addItemDecoration(decoration)
        bindState(viewModel.state, viewModel.accept)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireMainActivity().supportActionBar?.apply {
            title = getString(R.string.photo_gallery_fragment_title)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun bindState(
        uiState: LiveData<UiState>,
        uiActions: (UiAction) -> Unit
    ) {
        val galleryAdapter = PhotoAdapter(
            onClick = { photo ->
                navigator.showPhoto(photo)
                Toast.makeText(context, "${photo.id} pressed", Toast.LENGTH_SHORT).show()
            }
        )
        binding.photoRecyclerView.apply {
            setHasFixedSize(true)
//            layoutManager = LinearLayoutManager(context)
            layoutManager = GridLayoutManager(context, SPAN_COUNT)
            adapter = galleryAdapter

        }
        bindList(galleryAdapter, uiState, uiActions)
    }

    private fun bindList(
        repoAdapter: PhotoAdapter,
        uiState: LiveData<UiState>,
        onScrollChanged: (UiAction.Scroll) -> Unit
    ) {
        setupScrollListener(onScrollChanged)
        uiState
            .map(UiState::searchResult)
            .distinctUntilChanged()
            .observe(viewLifecycleOwner) { result ->
                when (result) {
                    is RepoSearchResult.Success -> {
                        showEmptyList(result.data.isEmpty())
                        repoAdapter.submitList(result.data)
                    }
                    is RepoSearchResult.Error -> {
                        Toast.makeText(
                            this.context,
                            "\uD83D\uDE28 Wooops $result.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
    }

    private fun setupScrollListener(
        onScrollChanged: (UiAction.Scroll) -> Unit
    ) {
//        val layoutManager = binding.photoRecyclerView.layoutManager as LinearLayoutManager
        val layoutManager = binding.photoRecyclerView.layoutManager as GridLayoutManager
        binding.photoRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = layoutManager.itemCount
                val visibleItemCount = layoutManager.childCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                onScrollChanged(
                    UiAction.Scroll(
                        visibleItemCount = visibleItemCount,
                        lastVisibleItemPosition = lastVisibleItem,
                        totalItemCount = totalItemCount
                    )
                )
            }
        })
    }

    private fun showEmptyList(show: Boolean) {
        binding.photoRecyclerView.isVisible = !show
    }

    companion object {
        fun newInstance() = PhotoGalleryFragment()
    }
}