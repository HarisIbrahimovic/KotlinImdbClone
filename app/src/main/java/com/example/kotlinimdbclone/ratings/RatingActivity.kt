package com.example.kotlinimdbclone.ratings

import android.os.Bundle
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinimdbclone.adapters.MovieRatingAdapter
import com.example.kotlinimdbclone.databinding.ActivityRatingBinding
import com.example.kotlinimdbclone.model.User
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RatingActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRatingBinding
    private val viewModel : RatingViewModel by viewModels()
    private lateinit var movieRatingAdapter: MovieRatingAdapter
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRatingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val id = intent.getStringExtra("id")
        viewModel.initViewModel(id!!)
        setUpRecView()
        observe()
    }

    private fun observe() {
        viewModel.listOfRatings.observe(this,{list->
            movieRatingAdapter.setList(list)
        })
        viewModel.userData.observe(this,{userData->
            user=userData
        })
    }

    private fun setUpRecView() {
        binding.commentText.setOnEditorActionListener(editorActionListener)
        movieRatingAdapter = MovieRatingAdapter(applicationContext)
        binding.ratingsRecView.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = movieRatingAdapter
        }
    }


    private val editorActionListener =
        OnEditorActionListener { _, _, _ ->
            val moviePicPath = intent.getStringExtra("moviePicPath")
            val movieName = intent.getStringExtra("movieName")
            val id = intent.getStringExtra("id")
            val comment: String = binding.commentText.text.toString()
            val score: Float = (binding.ratingBarRating.rating * 2)
            val profilePath = user.profilePath
            val userName = user.username
            if (viewModel.submitRating(id!!,
                    profilePath,moviePicPath!!, userName,movieName!!,comment,score)) {
                Toast.makeText(applicationContext, "Rating added", Toast.LENGTH_SHORT).show()
            } else Toast.makeText(applicationContext, "Please login", Toast.LENGTH_SHORT).show()
            true
        }
}