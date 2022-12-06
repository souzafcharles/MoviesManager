package br.edu.ifsp.ads.pdm.moviesmanager.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import br.edu.ifsp.ads.pdm.moviesmanager.R
import br.edu.ifsp.ads.pdm.moviesmanager.model.entity.Movie


class MovieAdapter(context : Context, private val movieList: MutableList<Movie>) : ArrayAdapter<Movie>(context, R.layout.tile_movie, movieList) {

    private data class TileMovieHolder(val nameTv: TextView, val genreAndYearTv: TextView, val gradeTv: TextView)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val movie = movieList[position]
        var movieTileView = convertView
        if (movieTileView == null) {

            movieTileView = (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(R.layout.tile_movie, parent, false)

            val tileMovieHolder = TileMovieHolder(
                movieTileView.findViewById(R.id.nameTv),
                movieTileView.findViewById(R.id.genreAndYearTv),
                movieTileView.findViewById(R.id.rateTv),
            )
            movieTileView.tag = tileMovieHolder
        }

        with(movieTileView?.tag as TileMovieHolder) {
            nameTv.text = movie.movieName
            genreAndYearTv.text = movie.movieGenre + " - " + movie.movieYear.toString()
            gradeTv.text = if(movie.movieRate != 11) " Nota: " + movie.movieRate.toString() else " Nota: Não atribuida"
        }

        return movieTileView
    }
}