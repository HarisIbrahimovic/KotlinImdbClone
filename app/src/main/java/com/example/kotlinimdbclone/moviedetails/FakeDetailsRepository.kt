package com.example.kotlinimdbclone.moviedetails

import com.example.kotlinimdbclone.model.MovieCredits
import com.example.kotlinimdbclone.model.MovieDetails
import com.example.kotlinimdbclone.util.Resource

class FakeDetailsRepository:DetailsRepository {


    val listOfCredits : MovieCredits? = null
    val details : MovieDetails? = null

    override suspend fun getMovieDetails(id: String): Resource<MovieDetails> {
        return if(id==""){
            Resource.Error("No data")
        }else Resource.Success(details!!)
    }

    override suspend fun getMovieCast(id: String): Resource<MovieCredits> {
        return if(id==""){
            Resource.Error("No data")
        }else Resource.Success(listOfCredits!!)
    }
}