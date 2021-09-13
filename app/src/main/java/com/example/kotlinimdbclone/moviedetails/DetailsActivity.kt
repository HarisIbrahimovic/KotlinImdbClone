package com.example.kotlinimdbclone.moviedetails

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.kotlinimdbclone.R
import com.example.kotlinimdbclone.actordetails.ActorActivity
import com.example.kotlinimdbclone.adapters.ActorAdapter
import com.example.kotlinimdbclone.databinding.ActivityDetailsBinding
import com.example.kotlinimdbclone.ratings.RatingActivity
import com.example.kotlinimdbclone.util.Constants
import com.example.kotlinimdbclone.util.IncomingMovieCredits
import com.example.kotlinimdbclone.util.IncomingMovieDetails
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() ,ActorAdapter.OnClickActorAdapter{

    private lateinit var binding: ActivityDetailsBinding
    private val viewModel:DetailsViewModel by viewModels()
    private lateinit var actorAdapter: ActorAdapter
    private lateinit var movieName : String
    private var moviePicPath : String= ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val id = intent.getStringExtra("id")
        viewModel.initViewModel(id!!)
        setUpRecView()
        observe()

        binding.ratings.setOnClickListener{
                val intent = Intent(applicationContext,RatingActivity::class.java)
                intent.putExtra("id",id)
                intent.putExtra("moviePicPath",moviePicPath)
                intent.putExtra("movieName",movieName)
                startActivity(intent)
        }

    }



    private fun observe() {
        viewModel.liveDataMovieDetails.observe(this,{detailsEvent->
            when(detailsEvent){
                is IncomingMovieDetails.Success->{

                    binding.progressBar.visibility = View.GONE
                    val details = detailsEvent.movieDetails
                    movieName = details.title
                    binding.DetailsMovieDescription.text= details.overview
                    binding.detailedMovieName.text= details.title
                    binding.detailedReleaseDate.text= details.release_date
                    binding.detailedRatingBar.rating= details.vote_average.toFloat()
                    val requestOptions = RequestOptions.centerCropTransform()
                    if(!details.poster_path.isNullOrEmpty()){ Glide.with(this)
                            .load("https://image.tmdb.org/t/p/w500/" + details.poster_path)
                            .apply(requestOptions)
                            .into(binding.detailedMovieImage)
                        moviePicPath = details.poster_path
                    }
                    else Glide.with(this)
                            .load(Constants.NO_IMAGE_MOVIE)
                            .apply(requestOptions)
                            .into(binding.detailedMovieImage)
                    try{
                        binding.detailedGenres.text= details.genres[0].name
                    }catch (e:Exception){
                        binding.detailedGenres.text= getString(R.string.Unknown)
                    }

                }
                is IncomingMovieDetails.Failure->{
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this,detailsEvent.errorMessage,Toast.LENGTH_SHORT).show()
                }
                is IncomingMovieDetails.Loading-> binding.progressBar.visibility = View.VISIBLE
                else -> Unit
            }
        })

        viewModel.liveDataMovieCredits.observe(this,{creditsEvent->
            when(creditsEvent){
                is IncomingMovieCredits.Success->{
                    binding.progressBar.visibility=View.GONE
                    actorAdapter.setList(creditsEvent.movieCredits)
                }
                is IncomingMovieCredits.Failure->{
                    binding.progressBar.visibility=View.GONE
                    Toast.makeText(this,creditsEvent.errorMessage,Toast.LENGTH_SHORT).show()
                }
                is IncomingMovieCredits.Loading-> binding.progressBar.visibility=View.VISIBLE
                else -> Unit
            }
        })

    }

    private fun setUpRecView() {
        actorAdapter = ActorAdapter(this,this)
        binding.actorRecView.apply {
            layoutManager = LinearLayoutManager(applicationContext,LinearLayoutManager.HORIZONTAL,false)
            adapter = actorAdapter
        }
    }

    override fun onActorClicked(id: String) {
        val intent = Intent(applicationContext, ActorActivity::class.java)
        intent.putExtra("id",id)
        startActivity(intent)
    }


}