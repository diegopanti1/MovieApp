package com.example.movieapplication.domain.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LocationModel(
    val latitude: String? = null,
    val longitude: String? = null,
    val date: String? = null
): Parcelable
