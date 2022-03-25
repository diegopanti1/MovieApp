package com.example.movieapplication.domain.events

import com.example.movieapplication.domain.models.LocationModel

data class NewLocationEvent (val location: LocationModel)