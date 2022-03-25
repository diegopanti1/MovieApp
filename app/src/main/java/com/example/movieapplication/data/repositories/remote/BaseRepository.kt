package com.example.movieapplication.data.repositories.remote

import com.example.movieapplication.domain.Resource
import com.example.movieapplication.domain.models.responses.ErrorResponse
import com.google.gson.Gson
import com.google.gson.JsonParseException
import retrofit2.Response

abstract class BaseRepository {
    suspend fun <T> getResponse(
        request: suspend () -> Response<T>,
        defaultErrorMessage: String
    ): Resource<T> {
        return try {
            val result = request.invoke()
            if (result.isSuccessful) {
                return Resource.Success(result.body()!!)
            } else {
                val errorJson = result.errorBody()?.string() ?: defaultErrorMessage
                val apiError = try {
                    Gson().fromJson(errorJson, ErrorResponse::class.java)
                } catch (e: JsonParseException) {
                    //TODO("Replace with a better handling error when backend standards are established")
                    null
                }
                Resource.Failure(
                    false,
                    apiError?.status_code,
                    if(apiError?.status_message != null) apiError.status_message else defaultErrorMessage
                )
            }
        } catch (e: Throwable) {
            Resource.Failure(false, 100, defaultErrorMessage)
        }
    }
}