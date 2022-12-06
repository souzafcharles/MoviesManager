package br.edu.ifsp.ads.pdm.moviesmanager.controller

import androidx.room.Room
import br.edu.ifsp.ads.pdm.moviesmanager.model.dao.MovieRoomDAO
import br.edu.ifsp.ads.pdm.moviesmanager.model.database.MovieRoomDatabase
import br.edu.ifsp.ads.pdm.moviesmanager.model.entity.Movie
import br.edu.ifsp.ads.pdm.moviesmanager.view.MainActivity

class MovieRoomController(private val mainActivity: MainActivity) {

    private val movieDAOImpl: MovieRoomDAO by lazy {
        Room.databaseBuilder(mainActivity, MovieRoomDatabase::class.java, MovieRoomDAO.MOVIE_DATABASE_FILE).build().getMovieRoomDao()
    }

    fun insert(movie: Movie) {
        Thread {
            movieDAOImpl.create(movie)
            getAll()
        }.start()
    }

    fun listOne(name: String) = movieDAOImpl.getOne(name)

    fun getAll() {

    }

    fun update(movie: Movie) {
        Thread {
            movieDAOImpl.update(movie)
            getAll()
        }.start()
    }

    fun delete(movie: Movie) {
        Thread {
            movieDAOImpl.delete(movie)
            getAll()
        }.start()
    }
}