package com.example.movieapplication.presentation.ui.main.gallery

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieapplication.domain.Resource
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(private val storage :FirebaseStorage) : ViewModel() {
    private val _uri = MutableLiveData<Uri>()
    val uri: LiveData<Uri> get() = _uri

    private val _imageUpload = MutableLiveData<Resource<Any>>()
    val imageUpload: LiveData<Resource<Any>> get() = _imageUpload

    fun setNewImageUri(uri: Uri) {
        _uri.postValue(uri)
    }

    fun uploadImage() {
        _imageUpload.postValue(Resource.Loading)
        val ref = storage.reference
            .child(
                "images/"
                        + UUID.randomUUID().toString()
            )
        ref.putFile(_uri.value!!)
            .addOnSuccessListener {
                _imageUpload.postValue(Resource.Success(it.totalByteCount))
            }
            .addOnFailureListener {
                _imageUpload.postValue(Resource.Failure(false, 500, it.message.toString()))
            }
    }
}