package br.edu.ifsp.ads.pdm.moviesmanager.view
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import br.edu.ifsp.ads.pdm.moviesmanager.R
import br.edu.ifsp.ads.pdm.moviesmanager.databinding.ActivityMovieBinding
import br.edu.ifsp.ads.pdm.moviesmanager.model.Constant.EXTRA_MOVIE
import br.edu.ifsp.ads.pdm.moviesmanager.model.Constant.VIEW_MOVIE
import br.edu.ifsp.ads.pdm.moviesmanager.model.entity.Movie


class MovieActivity : AppCompatActivity()  {

    private val activityMovieBinding: ActivityMovieBinding by lazy {
        ActivityMovieBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityMovieBinding.root)
        activityMovieBinding.rateSp.adapter = ArrayAdapter<String>(this, R.layout.simple_spinner_dropdown_item, resources.getStringArray(R.array.hatesChoice))
        activityMovieBinding.rateSp.adapter = ArrayAdapter<String>(this, R.layout.simple_spinner_dropdown_item, resources.getStringArray(R.array.genresChoice))
        activityMovieBinding.rateSp.setSelection(11)
        activityMovieBinding.rateSp.isEnabled = false

        val receivedPerson =  intent.getParcelableExtra<Movie>(EXTRA_MOVIE)
        receivedPerson?.let{ _receivedPerson ->
            activityMovieBinding.titleTv.text = "Editar Filme"
            activityMovieBinding.nameEt.isEnabled = false
            with(activityMovieBinding)
            {
                with(_receivedPerson)
                {
                    nameEt.setText(movieName)
                    yearEt.setText(movieYear.toString())
                    studioorproducerEt.setText(movieStudioOrProducer)
                    durationEt.setText(movieDuration.toString())
                    rateSp.setSelection(movieRate)
                    watchedCb.isChecked = movieWatched
                    genreSp.setSelection(resources.getStringArray(R.array.genresChoice).indexOf(movieGenre))
                    if(movieWatched) rateSp.isEnabled = true;
                }
            }
        }

        if (intent.getBooleanExtra(VIEW_MOVIE, false)) {
            activityMovieBinding.titleTv.text = "Visualizar Filme"
            activityMovieBinding.nameEt.isEnabled = false
            activityMovieBinding.yearEt.isEnabled = false
            activityMovieBinding.studioorproducerEt.isEnabled = false
            activityMovieBinding.durationEt.isEnabled = false
            activityMovieBinding.rateSp.isEnabled = false
            activityMovieBinding.watchedCb.isEnabled = false
            activityMovieBinding.genreSp.isEnabled = false
            activityMovieBinding.saveBt.visibility = View.GONE
        }

        activityMovieBinding.saveBt.setOnClickListener {
            val movieName = activityMovieBinding.nameEt.text.toString()
            val movieYear = activityMovieBinding.yearEt.text.toString()
            val movieStudio = activityMovieBinding.studioorproducerEt.text.toString()
            val movieDuration = activityMovieBinding.durationEt.text.toString()
            val movieGrade = activityMovieBinding.rateSp.selectedItemPosition;
            val movieWatched = activityMovieBinding.watchedCb.isChecked
            val movieGenre = activityMovieBinding.genreSp.selectedItem.toString()

            if (movieName.isNullOrEmpty() || movieYear.isNullOrEmpty() || movieYear.isNullOrEmpty() || movieDuration.isNullOrEmpty() || movieGenre.isNullOrEmpty()) Toast.makeText(this, "Há campos preenchidos incorretamente.", Toast.LENGTH_SHORT).show()
            else {
                val movie = Movie(
                    movieName = movieName,
                    movieYear = movieYear.toInt(),
                    movieStudioOrProducer = movieStudio,
                    movieDuration = movieDuration.toInt(),
                    movieWatched = movieWatched,
                    movieRate = movieGrade,
                    movieGenre = movieGenre
                )
                val resultIntent = Intent()
                resultIntent.putExtra(EXTRA_MOVIE, movie)
                setResult(RESULT_OK, resultIntent)
                finish()
            }
        }

        activityMovieBinding.backBt.setOnClickListener{
            setResult(RESULT_CANCELED)
            finish()
        }

        activityMovieBinding.watchedCb.setOnCheckedChangeListener { _, isChecked ->
            if(!isChecked){
                activityMovieBinding.rateSp.setSelection(11)
            }
            activityMovieBinding.rateSp.isEnabled = isChecked
        }
    }
}