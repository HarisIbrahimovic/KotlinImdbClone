package com.example.kotlinimdbclone.moviedetails

import com.example.kotlinimdbclone.model.MovieCredits
import com.example.kotlinimdbclone.model.MovieDetails
import com.example.kotlinimdbclone.util.Resource

interface DetailsRepository {
    suspend fun getMovieDetails(id:String): Resource<MovieDetails>
    suspend fun getMovieCast(id:String): Resource<MovieCredits>
}