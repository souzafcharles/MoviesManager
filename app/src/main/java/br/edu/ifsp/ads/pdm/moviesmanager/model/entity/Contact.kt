package br.edu.ifsp.ads.pdm.moviesmanager.model.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "movie")
data class Movie(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "movie_name")
    var movieName: String,
    @ColumnInfo(name = "release_year")
    var movieReleaseYear: Int,
    var movieStudioOrProducer: String,
    var movieDuration: Int,
    var movieWatched: Int,
    var movieRate: Double?,
    var movieGenreChoiceId: Int?
) : Parcelable