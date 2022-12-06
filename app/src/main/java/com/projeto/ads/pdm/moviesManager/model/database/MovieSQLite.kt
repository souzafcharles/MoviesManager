package com.projeto.ads.pdm.moviesManager.model.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.projeto.ads.pdm.moviesManager.R
import com.projeto.ads.pdm.moviesManager.model.dao.MovieDAO
import com.projeto.ads.pdm.moviesManager.model.entity.Movie
import java.lang.Boolean.getBoolean
import java.sql.SQLException

class MovieSQLite(context: Context) : MovieDAO {

    companion object Constant {
        private const val MOVIE_DATABASE_FILE = "movie_room"
        private const val MOVIE_TABLE = "movie"
        private const val MOVIE_NAME_COLUMN = "name"
        private const val MOVIE_YEAR_COLUMN = "year"
        private const val MOVIE_STUDIO_COLUMN = "studio"
        private const val MOVIE_DURATION_COLUMN = "duration"
        private const val MOVIE_WATCHED_COLUMN = "watched"
        private const val MOVIE_GRADE_COLUMN = "grade"
        private const val MOVIE_GENRE_COLUMN = "genre"

        private const val CREATE_MOVIE_TABLE_STATEMENT =
            "CREATE TABLE IF NOT EXISTS $MOVIE_TABLE ( " +
                    "$MOVIE_NAME_COLUMN TEXT PRIMARY KEY NOT NULL, " +
                    "$MOVIE_YEAR_COLUMN INT NOT NULL, " +
                    "$MOVIE_STUDIO_COLUMN TEXT NOT NULL, " +
                    "$MOVIE_DURATION_COLUMN INT NOT NULL, " +
                    "$MOVIE_WATCHED_COLUMN BOOLEAN NOT NULL," +
                    "$MOVIE_GRADE_COLUMN INT," +
                    "$MOVIE_GENRE_COLUMN TEXT NOT NULL);"
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
        put(MOVIE_NAME_COLUMN, name)
        put(MOVIE_YEAR_COLUMN, year)
        put(MOVIE_STUDIO_COLUMN, studio)
        put(MOVIE_DURATION_COLUMN, duration)
        put(MOVIE_WATCHED_COLUMN, watched)
        put(MOVIE_GRADE_COLUMN, grade)
        put(MOVIE_GENRE_COLUMN, genre)
        this
    }

    private fun movieToContentValues(movie: Movie) = with(ContentValues()) {
        put(MOVIE_NAME_COLUMN, movie.name)
        put(MOVIE_YEAR_COLUMN, movie.year)
        put(MOVIE_STUDIO_COLUMN, movie.studio)
        put(MOVIE_DURATION_COLUMN, movie.duration)
        put(MOVIE_WATCHED_COLUMN, movie.watched)
        put(MOVIE_GRADE_COLUMN, movie.grade)
        put(MOVIE_GENRE_COLUMN, movie.genre)
        this
    }

    private fun Cursor.rowToMovie() = Movie(
        getString(getColumnIndexOrThrow(MOVIE_NAME_COLUMN)),
        getInt(getColumnIndexOrThrow(MOVIE_YEAR_COLUMN)),
        getString(getColumnIndexOrThrow(MOVIE_STUDIO_COLUMN)),
        getInt(getColumnIndexOrThrow(MOVIE_DURATION_COLUMN)),
        getBoolean(getColumnIndexOrThrow(MOVIE_WATCHED_COLUMN).toString()),
        getInt(getColumnIndexOrThrow(MOVIE_GRADE_COLUMN)),
        getString(getColumnIndexOrThrow(MOVIE_GENRE_COLUMN))
    )

    override fun create(movie: Movie) = movieSqliteDatabase.insert(MOVIE_TABLE, null, movieToContentValues(movie)).toInt()

    override fun getOne(name: String): Movie? {
        val cursor = movieSqliteDatabase.rawQuery("SELECT * FROM $MOVIE_TABLE WHERE $MOVIE_NAME_COLUMN = ?", arrayOf(name))
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

    override fun update(movie: Movie) = movieSqliteDatabase.update(MOVIE_TABLE, movie.toContentValues(), "$MOVIE_NAME_COLUMN = ?", arrayOf(movie.name))

    override fun delete(name: String) = movieSqliteDatabase.delete(MOVIE_TABLE, "$MOVIE_NAME_COLUMN = ?", arrayOf(name))
}