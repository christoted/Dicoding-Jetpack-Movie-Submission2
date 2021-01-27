package com.example.mymovie.data.entity

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val Poster: String,
    val Title: String,
    val Type: String,
    val Year: String,
    val imdbID: String
) : Parcelable
