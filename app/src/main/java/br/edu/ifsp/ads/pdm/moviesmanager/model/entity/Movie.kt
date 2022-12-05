package br.edu.ifsp.ads.pdm.moviesmanager.model.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Movie(
    @PrimaryKey
    var movieName: String,
    @NonNull
    var movieYear : Int,
    @NonNull
    var movieStudioOrProducer: String,
    @NonNull
    var movieDuration : Int,
    @NonNull
    var movieWatched : Boolean,
    var movieRate : Int,
    @NonNull
    var moviegenre : String,
) : Parcelable