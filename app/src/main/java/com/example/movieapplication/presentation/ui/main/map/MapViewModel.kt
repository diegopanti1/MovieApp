package com.example.movieapplication.presentation.ui.main.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieapplication.domain.Resource
import com.example.movieapplication.domain.models.LocationModel
import com.example.movieapplication.platform.NetworkHandler
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val networkHandler: NetworkHandler,
    private val db: FirebaseFirestore
) : ViewModel() {
    private val _locations = MutableLiveData<Resource<List<LocationModel>>>()
    val locations: LiveData<Resource<List<LocationModel>>> get() = _locations

    fun getLocations() {
        if (networkHandler.isConnected) {
            db.collection("locations")
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val models =
                            task.result!!.map { document -> document.toObject(LocationModel::class.java) }
                        _locations.postValue(Resource.Success(models))

                    } else {
                        _locations.postValue(
                            Resource.Failure(
                                false,
                                500,
                                "Error getting documents."
                            )
                        )
                    }
                }

        }
    }

}