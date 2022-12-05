package br.edu.ifsp.ads.pdm.moviesmanager.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.edu.ifsp.ads.pdm.moviesmanager.model.dao.MovieRoomDAO
import br.edu.ifsp.ads.pdm.moviesmanager.model.entity.Movie


@Database(entities = [Movie::class], version = 1)
abstract class MovieRoomDatabase: RoomDatabase() {
    abstract fun getMovieRoomDao(): MovieRoomDAO
}