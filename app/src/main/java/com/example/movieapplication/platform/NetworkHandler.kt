package com.example.movieapplication.platform

import android.content.Context
import com.example.movieapplication.extension.networkInfo
import javax.inject.Inject

class NetworkHandler @Inject constructor(private val context: Context){
    val isConnected get() = context.networkInfo?.isConnectedOrConnecting == true
}