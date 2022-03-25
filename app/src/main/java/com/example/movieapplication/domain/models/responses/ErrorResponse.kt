package com.example.movieapplication.domain.models.responses

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ErrorResponse(
    val status_code: Int,
    val status_message: String,
    val success: Boolean
): Parcelable
