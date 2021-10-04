package com.minchinovich.rsandroidtask5.ui.photo

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import coil.imageLoader
import coil.load
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.google.android.material.snackbar.Snackbar
import com.minchinovich.rsandroidtask5.data.entities.GalleryItem
import com.minchinovich.rsandroidtask5.databinding.PhotoFragmentBinding
import com.minchinovich.rsandroidtask5.utils.requireApp
import com.minchinovich.rsandroidtask5.utils.requireMainActivity
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import android.R




class PhotoFragment : Fragment() {

    private val PERMISSION_REQUEST_WRITE_STORAGE = 0
    private lateinit var galleryItem: GalleryItem

    private val viewModel: PhotoViewModel by viewModels {
        requireApp().viewModelFactory
    }
    private var _binding: PhotoFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        galleryItem = arguments?.getSerializable(ITEM) as GalleryItem
        _binding = PhotoFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireMainActivity().supportActionBar?.apply {
            title = galleryItem.id + ".jpg"
        }
        binding.imageView.load(galleryItem.url)

        binding.saveButton.setOnClickListener {

            if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED){
                requireApp().applicationScope.launch{
                    val context: Context = requireContext()
                    val loader = context.imageLoader
                    val request = ImageRequest.Builder(context)
                        .data(galleryItem.url)
                        .allowHardware(false) // Disable hardware bitmaps.
                        .build()

                    val result = (loader.execute(request) as SuccessResult).drawable
                    val bitmap = (result as BitmapDrawable).bitmap

                    saveImage(bitmap, 100, galleryItem.id, "caturday")

                }
                binding.saveButton.visibility = View.GONE
            } else {
                requestWritePermission()
            }

        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private fun  requestWritePermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            Snackbar.make(
                binding.root, "R.string.write_access_required",
                Snackbar.LENGTH_INDEFINITE
            ).setAction(R.string.ok, View.OnClickListener { // Request the permission
                ActivityCompat.requestPermissions(
                    requireActivity(), arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    PERMISSION_REQUEST_WRITE_STORAGE
                )
            }).show()
        } else {
            Snackbar.make(binding.root, "R.string.camera_unavailable", Snackbar.LENGTH_SHORT).show()
            // Request the permission. The result will be received in onRequestPermissionResult().
            ActivityCompat.requestPermissions(requireActivity(),
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                PERMISSION_REQUEST_WRITE_STORAGE
            )
        }
    }

    private fun saveImage(
        bitmap: Bitmap,
        quality: Int,
        fileName: String?,
        folderName: String?)
    {
        val filename = "${fileName ?: System.currentTimeMillis()}.jpg"

        var directory: String = Environment.DIRECTORY_PICTURES
        if (folderName != null) {
            directory = "${directory}${File.separator}${folderName}"
        }

        var fos: OutputStream? = null

        var resultUri: Uri? = null

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            context?.contentResolver?.also { resolver ->
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, directory)
                    put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis())
                }
                val imageUri: Uri? =
                    resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

                fos = imageUri?.let { resolver.openOutputStream(it) }

                resultUri = imageUri
            }
        } else {
            val storePath =
                Environment.getExternalStorageDirectory().absolutePath + File.separator + directory

            val appDir = File(storePath)
            if (!appDir.exists()) {
                appDir.mkdir()
            }
            val file = File(appDir, filename)
            if (!file.exists()) {
                file.createNewFile()
            }

            fos = FileOutputStream(file)

            resultUri = Uri.fromFile(file)
        }

        fos?.use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, it)
            it.flush()
            it.close()
        }
        bitmap.recycle()
    }

    companion object {
        private const val ITEM = "item"

        fun newInstance(galleryItem: GalleryItem): PhotoFragment {
           val fragment = PhotoFragment()
           fragment.arguments = Bundle().apply {
               putSerializable(ITEM, galleryItem)
           }
            return fragment
        }
    }

}