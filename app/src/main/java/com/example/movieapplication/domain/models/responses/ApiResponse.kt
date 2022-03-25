package com.example.movieapplication.domain.models.responses

import java.math.BigInteger

data class ApiResponse<D>(
    val page: Int,
    val results: List<D>,
    val total_pages: BigInteger,
    val total_results: BigInteger
)