package com.example.mymovie.data.local.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "tvshowentities")
@Parcelize
data class TvShow(
    @ColumnInfo(name = "poster")
    var Poster: String,
    @ColumnInfo(name = "title")
    var Title: String,
    @ColumnInfo(name = "type")
    var Type: String,
    @ColumnInfo(name = "year")
    var Year: String,
    @PrimaryKey
    @ColumnInfo(name = "imbdid")
    var imdbID: String,
    @ColumnInfo(name = "bookmarked")
    var bookmarked: Boolean = false
) : Parcelable