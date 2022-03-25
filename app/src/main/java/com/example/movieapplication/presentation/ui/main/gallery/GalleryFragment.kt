package com.example.movieapplication.presentation.ui.main.gallery

import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.movieapplication.R
import com.example.movieapplication.databinding.GalleryFragmentBinding
import com.example.movieapplication.domain.Resource
import com.example.movieapplication.presentation.ui.dialogs.loader.LoaderFragment
import com.example.movieapplication.utils.AlertDialogUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GalleryFragment : Fragment(R.layout.gallery_fragment) {

    private var _binding: GalleryFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: GalleryViewModel
    private lateinit var observer: MyLifecycleObserver

    private var loaderFragment: LoaderFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observer = MyLifecycleObserver(requireActivity().activityResultRegistry)
        lifecycle.addObserver(observer)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = GalleryFragmentBinding.bind(view)
        viewModel = ViewModelProvider(this).get(GalleryViewModel::class.java)

        binding.pickImageBtn.setOnClickListener {
            // Open the activity to select an image
            observer.selectImage(viewModel)
        }

        observeResponse()
    }

    private fun observeResponse() = lifecycleScope.launchWhenStarted {
        viewModel.uri.observe(viewLifecycleOwner) {
            val response = it ?: return@observe
            try {
                val bitmap =
                    MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, response)
                binding.image.setImageBitmap(bitmap)
                viewModel.uploadImage()
            } catch (ex: Exception) {

            }
        }
        viewModel.imageUpload.observe(viewLifecycleOwner) {
            val response = it ?: return@observe
            when(response){
                is Resource.Success -> {
                    AlertDialogUtil.confirmationDialog(
                        context = requireContext(),
                        title = R.string.notice,
                        message = R.string.upload_successful,
                    )
                    binding.image.setImageResource(R.drawable.ic_image_icon)
                }
                is Resource.Failure -> {
                    Toast.makeText(
                        requireContext(),
                        response.errorBody.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is Resource.Loading -> {
                    if (loaderFragment == null) {
                        loaderFragment = LoaderFragment.newInstance(getString(R.string.loading))
                        loaderFragment?.show(parentFragmentManager, "LOADER_SIGNUP")
                    }
                }
            }
            if (!response.isLoading && loaderFragment != null) {
                loaderFragment?.dismiss()
                loaderFragment = null
            }
        }
    }

}

class MyLifecycleObserver(private val registry: ActivityResultRegistry) : DefaultLifecycleObserver {
    private lateinit var getContent: ActivityResultLauncher<String>
    lateinit var viewModel: GalleryViewModel
    override fun onCreate(owner: LifecycleOwner) {
        getContent = registry.register("key", owner, ActivityResultContracts.GetContent()) { uri ->
            viewModel.setNewImageUri(uri)
        }
    }

    fun selectImage(viewModel: GalleryViewModel) {
        this.viewModel = viewModel
        getContent.launch("image/*")
    }
}