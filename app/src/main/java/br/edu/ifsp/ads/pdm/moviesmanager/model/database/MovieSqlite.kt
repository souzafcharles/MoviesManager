package br.edu.ifsp.ads.pdm.moviesmanager.model.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import br.edu.ifsp.ads.pdm.moviesmanager.R
import br.edu.ifsp.ads.pdm.moviesmanager.model.entity.Movie
import br.edu.ifsp.ads.pdm.moviesmanager.model.dao.MovieDAO
import java.lang.Boolean.getBoolean
import java.sql.SQLException

class MovieSQLite(context: Context) : MovieDAO {

    companion object Constant {
        private const val MOVIE_DATABASE_FILE = "movie_room"
        private const val MOVIE_TABLE = "movie"
        private const val NAME_COLUMN = "name"
        private const val YEAR_COLUMN = "year"
        private const val STUDIOORPRODUCER_COLUMN = "studioorproducer"
        private const val DURATION_COLUMN = "duration"
        private const val WATCHED_COLUMN = "watched"
        private const val RATE_COLUMN = "rate"
        private const val GENRE_COLUMN = "genre"

        private const val CREATE_MOVIE_TABLE_STATEMENT =
            "CREATE TABLE IF NOT EXISTS $MOVIE_TABLE ( " +
                    "$NAME_COLUMN TEXT PRIMARY KEY NOT NULL, " +
                    "$YEAR_COLUMN INT NOT NULL, " +
                    "$STUDIOORPRODUCER_COLUMN TEXT NOT NULL, " +
                    "$DURATION_COLUMN INT NOT NULL, " +
                    "$WATCHED_COLUMN BOOLEAN NOT NULL," +
                    "$RATE_COLUMN INT," +
                    "$GENRE_COLUMN TEXT NOT NULL);"
    }

    private val movieSqliteDatabase: SQLiteDatabase

    init {
        movieSqliteDatabase = context.openOrCreateDatabase(
            MOVIE_DATABASE_FILE,
            Context.MODE_PRIVATE,
            null
        )
        try {
            movieSqliteDatabase.execSQL(CREATE_MOVIE_TABLE_STATEMENT)
        } catch (se: SQLException) {
            Log.e(context.getString(R.string.app_name), se.toString())
        }
    }

    private fun Movie.toContentValues() = with(ContentValues()) {
        put(NAME_COLUMN, movieName)
        put(YEAR_COLUMN, movieYear)
        put(STUDIOORPRODUCER_COLUMN, movieStudioOrProducer)
        put(DURATION_COLUMN, movieDuration)
        put(WATCHED_COLUMN, movieWatched)
        put(RATE_COLUMN, movieRate)
        put(GENRE_COLUMN, movieGenre)
        this
    }

    private fun movieToContentValues(movie: Movie) = with(ContentValues()) {
        put(NAME_COLUMN, movie.movieName)
        put(YEAR_COLUMN, movie.movieYear)
        put(STUDIOORPRODUCER_COLUMN, movie.movieStudioOrProducer)
        put(DURATION_COLUMN, movie.movieDuration)
        put(WATCHED_COLUMN, movie.movieWatched)
        put(RATE_COLUMN, movie.movieRate)
        put(GENRE_COLUMN, movie.movieGenre)
        this
    }

    private fun Cursor.rowToMovie() = Movie(
        getString(getColumnIndexOrThrow(NAME_COLUMN)),
        getInt(getColumnIndexOrThrow(YEAR_COLUMN)),
        getString(getColumnIndexOrThrow(STUDIOORPRODUCER_COLUMN)),
        getInt(getColumnIndexOrThrow(DURATION_COLUMN)),
        getBoolean(getColumnIndexOrThrow(WATCHED_COLUMN).toString()),
        getInt(getColumnIndexOrThrow(RATE_COLUMN)),
        getString(getColumnIndexOrThrow(GENRE_COLUMN))
    )

    override fun create(movie: Movie) = movieSqliteDatabase.insert(MOVIE_TABLE, null, movieToContentValues(movie)).toInt()

    override fun getOne(name: String): Movie? {
        val cursor = movieSqliteDatabase.rawQuery("SELECT * FROM $MOVIE_TABLE WHERE $NAME_COLUMN = ?", arrayOf(name))
        val movie = if (cursor.moveToFirst()) cursor.rowToMovie() else null

        cursor.close()
        return movie
    }

    override fun getAll(): MutableList<Movie> {
        val movieList = mutableListOf<Movie>()
        val cursor = movieSqliteDatabase.rawQuery("SELECT * FROM $MOVIE_TABLE", null)
        while (cursor.moveToNext()) { movieList.add(cursor.rowToMovie()) }

        cursor.close()
        return movieList
    }

    override fun update(movie: Movie) = movieSqliteDatabase.update(MOVIE_TABLE, movie.toContentValues(), "$NAME_COLUMN = ?", arrayOf(movie.movieName))

    override fun delete(name: String) = movieSqliteDatabase.delete(MOVIE_TABLE, "$NAME_COLUMN = ?", arrayOf(name))
}