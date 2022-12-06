package com.charles.ads.pdm.moviesmanager.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.charles.ads.pdm.moviesmanager.R
import com.charles.ads.pdm.moviesmanager.databinding.ActivityMainBinding
import com.charles.ads.pdm.moviesManager.adapter.MovieAdapter
import com.charles.ads.pdm.moviesManager.view.MovieActivity
import com.charles.ads.pdm.moviesmanager.controller.MovieRoomController
import com.charles.ads.pdm.moviesmanager.model.entity.Movie
import com.charles.ads.pdm.moviesmanager.model.Constant.EXTRA_MOVIE
import com.charles.ads.pdm.moviesmanager.model.Constant.VIEW_MOVIE


class MainActivity : AppCompatActivity() {

    private val activityMainBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var movieActivityResultLauncher: ActivityResultLauncher<Intent>

    private val movieList: MutableList<Movie> = mutableListOf()

    private var adding : Boolean = false

    private val movieController: MovieRoomController by lazy {
        MovieRoomController(this)
    }

    private lateinit var movieAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityMainBinding.root)

        movieAdapter = MovieAdapter(this, movieList)
        activityMainBinding.movieLv.adapter = movieAdapter

        movieActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        { result ->
            if (result.resultCode != RESULT_OK) Toast.makeText(this, "Cancelamento da ação", Toast.LENGTH_SHORT).show()
            else {
                val movie = result.data?.getParcelableExtra<Movie>(EXTRA_MOVIE)
                movie?.let { _movie->
                    val position = movieList.indexOfFirst { it.name == movie.name }
                    if (position != -1) {
                        if(!adding) {
                            movieController.update(_movie)
                        } else {
                            Toast.makeText(this, "Já existe um resgistro de filme com esse nome!.", Toast.LENGTH_LONG).show()
                        }
                    } else {
                        movieController.insert(_movie)
                    }
                    movieAdapter.notifyDataSetChanged()
                }
            }
            adding = false
        }

        registerForContextMenu(activityMainBinding.movieLv)

        activityMainBinding.movieLv.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                val movieIntent = Intent(this@MainActivity, MovieActivity::class.java)
                movieIntent.putExtra(EXTRA_MOVIE, movieList[position])
                movieIntent.putExtra(VIEW_MOVIE, true)
                startActivity(movieIntent)
            }

        movieController.getAll()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.filterMovieNameMi -> {
                if (movieList.size <= 0) Toast.makeText(this, "Não há registros de filmes cadastrados.", Toast.LENGTH_SHORT).show()
                else {
                    movieList.sortBy { it.name }
                    movieAdapter.notifyDataSetChanged()
                }
                true
            }
            R.id.filterMovieGradeMi -> {
                if (movieList.size <= 0) Toast.makeText(this, "Não há registros de filmes cadastrados.", Toast.LENGTH_SHORT).show()
                else {
                    movieList.sortBy { it.grade }
                    movieAdapter.notifyDataSetChanged()
                }
                true
            }
            R.id.addMovieMi -> {
                movieActivityResultLauncher.launch(Intent(this, MovieActivity::class.java))
                adding = true
                true
            }
            else -> { false }
        }
    }

    override fun onCreateContextMenu(menu: ContextMenu?, view: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        menuInflater.inflate(R.menu.context_menu_main, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val movie = movieList[(item.menuInfo as AdapterView.AdapterContextMenuInfo).position]
        return when(item.itemId) {
            R.id.editMovieMi -> {
                val movieIntent = Intent(this, MovieActivity::class.java)
                movieIntent.putExtra(EXTRA_MOVIE, movie)
                movieIntent.putExtra(VIEW_MOVIE, false)
                movieActivityResultLauncher.launch(movieIntent)
                true
            }
            R.id.removeMovieMi -> {
                movieController.delete(movie)
                movieAdapter.notifyDataSetChanged()
                true
            }
            else -> { false }
        }
    }

    fun updateMovieList(_movieList: MutableList<Movie>) {
        movieList.clear()
        movieList.addAll(_movieList)
        movieAdapter.notifyDataSetChanged()
    }
}