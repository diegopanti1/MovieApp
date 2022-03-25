package com.example.movieapplication.data.remote

import com.example.movieapplication.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthorizationInterceptor @Inject constructor(): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalHttpUrl = original.url()
        val url = originalHttpUrl.newBuilder().addQueryParameter("api_key", BuildConfig.API_KEY)
            .addQueryParameter("language", "es-MX").build()
        val builder = original.newBuilder().url(url)
        return chain.proceed(builder.build())
    }
}