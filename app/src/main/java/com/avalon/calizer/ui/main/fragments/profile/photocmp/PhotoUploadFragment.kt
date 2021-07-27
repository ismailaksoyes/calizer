package com.avalon.calizer.ui.main.fragments.profile.photocmp

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.avalon.calizer.data.local.profile.photoanalyze.PhotoAnalyzeData
import com.avalon.calizer.databinding.FragmentPhotoUploadBinding
import com.avalon.calizer.utils.showSnackBar
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class PhotoUploadFragment : Fragment() {
    private lateinit var binding: FragmentPhotoUploadBinding

    @Inject
    lateinit var viewModel: PhotoAnalyzeViewModel

    val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val imageUriList = ArrayList<Uri>()
                result.data?.let { itdata ->
                    itdata.clipData?.let { itClipData ->
                        val itemCount = itClipData.itemCount
                        for (i in 0 until itemCount) {
                            val imageUri: Uri = itClipData.getItemAt(i).uri
                            imageUriList.add(imageUri)

                        }
                    } ?: kotlin.run {
                        //single image selected
                        itdata.data?.let { itUri ->
                            val imageUri: Uri = itUri
                            imageUriList.add(imageUri)
                        }

                    }
                    createListBitmap(imageUriList)


                }

            }
        }


    private fun createListBitmap(uriList: List<Uri>) {
        if (uriList.isNotEmpty()) {
            val bitmapList = ArrayList<Bitmap?>()

            uriList.forEach { itUri ->
                bitmapList.add(uriToBitmap(itUri))


            }.also {
                setDataAnalyze(bitmapList)
            }

        } else {
            Toast.makeText(requireContext(), "RESIM YOK", Toast.LENGTH_SHORT).show()
        }

    }

    private fun uriToBitmap(imagePath: Uri): Bitmap? {
        val bitmap = if (Build.VERSION.SDK_INT < 29) {
            @Suppress("DEPRECATION")
            MediaStore.Images.Media.getBitmap(requireContext().contentResolver, imagePath)
        } else {
            ImageDecoder.decodeBitmap(
                ImageDecoder.createSource(
                    requireContext().contentResolver,
                    imagePath
                )
            )
        }
        return bitmap.copy(Bitmap.Config.ARGB_8888, bitmap.isMutable)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPhotoUploadBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
    }


    private fun getGalleryImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.action = Intent.ACTION_GET_CONTENT
        startForResult.launch(intent)

    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            getGalleryImage()
        } else {
            val intent = Intent()
            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            intent.addCategory(Intent.CATEGORY_DEFAULT)
            intent.data = Uri.parse("package:${requireActivity().packageName}")
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
            intent.flags = Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
            requireActivity().startActivity(intent)
        }

    }

    private fun onclickRequestPermission(view: View) {

        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED -> {
                getGalleryImage()
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) -> {
                binding.clTopComp.showSnackBar(
                    view,
                    "PERMISSION REQUIRED",
                    Snackbar.LENGTH_INDEFINITE,
                    "OK"
                ) {
                    requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                }

            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }

    }

    private fun initData() {
        binding.btnUploadImage.setOnClickListener {

            onclickRequestPermission(it)

        }
    }

    private fun setDataAnalyze(bitmapList: List<Bitmap?>) {
        val list = ArrayList<PhotoAnalyzeData>()
        bitmapList.forEachIndexed { index, itBitmap ->
            list.add(PhotoAnalyzeData(index, itBitmap, false))

        }
        val newList = list.toTypedArray()
        val action =
            PhotoUploadFragmentDirections.actionPhotoUploadFragmentToPhotoAnalyzeLoadingFragment(
                newList
            )
        findNavController().navigate(action)
    }

}


