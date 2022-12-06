package br.edu.ifsp.ads.pdm.moviesmanager.adapter

import android.content.Context
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

        return movieTileView
    }
}