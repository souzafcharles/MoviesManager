package br.edu.ifsp.ads.pdm.moviesmanager.view

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.AdapterContextMenuInfo
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.ads.pdm.moviesmanager.R
import br.edu.ifsp.ads.pdm.moviesmanager.adapter.MovieAdapter
import br.edu.ifsp.ads.pdm.moviesmanager.controller.MovieRoomController
import br.edu.ifsp.ads.pdm.moviesmanager.databinding.ActivityMainBinding
import br.edu.ifsp.ads.pdm.moviesmanager.model.Constant.EXTRA_MOVIE
import br.edu.ifsp.ads.pdm.moviesmanager.model.Constant.VIEW_MOVIE
import br.edu.ifsp.ads.pdm.moviesmanager.model.entity.Movie

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
            if (result.resultCode == RESULT_OK) {
                val movie = result.data?.getParcelableExtra<Movie>(EXTRA_MOVIE)
                movie?.let { _movie->
                    val position = movieList.indexOfFirst { it.movieName == movie.movieName }
                    if (position == -1) {
                        movieController.insert(_movie)
                    } else {
                        if(!adding) {
                            movieController.update(_movie)
                        } else {
                            Toast.makeText(this, "Esse filme já está cadastrado!", Toast.LENGTH_LONG).show()
                        }
                    }
                    movieAdapter.notifyDataSetChanged()
                }
            }
            else Toast.makeText(this, "Operação não permitida.", Toast.LENGTH_SHORT).show()
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

    fun updateMovieList(_movieList: MutableList<Movie>) {
        movieList.clear()
        movieList.addAll(_movieList)
        movieAdapter.notifyDataSetChanged()
    }
}