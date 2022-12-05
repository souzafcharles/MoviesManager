package br.edu.ifsp.ads.pdm.moviesmanager.model.dao

import br.edu.ifsp.ads.pdm.moviesmanager.model.entity.Movie

interface MovieDAO {
    fun create(movie: Movie): Int
    fun getOne(name: String): Movie?
    fun getAll(): MutableList<Movie>
    fun update(movie: Movie): Int
    fun delete(name: String): Int
}