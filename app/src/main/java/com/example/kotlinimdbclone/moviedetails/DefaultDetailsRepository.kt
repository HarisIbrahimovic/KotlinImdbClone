package com.example.kotlinimdbclone.moviedetails

import com.example.kotlinimdbclone.apiservice.ApiService
import com.example.kotlinimdbclone.model.MovieCredits
import com.example.kotlinimdbclone.model.MovieDetails
import com.example.kotlinimdbclone.util.Constants
import com.example.kotlinimdbclone.util.Resource
import javax.inject.Inject

class DefaultDetailsRepository
@Inject
constructor(private val apiService: ApiService) :DetailsRepository {

    override suspend fun getMovieDetails(id: String): Resource<MovieDetails> {
        return try {
            val response = apiService.getSingleMovie(id, Constants.API_KEY)
            val result = response.body()
            if(response.isSuccessful&&result!=null){
                Resource.Success(result)
            }else Resource.Error("Unknown error occurred")
        }catch (e:Exception){
            Resource.Error("Network error occurred")
        }
    }

    override suspend fun getMovieCast(id: String): Resource<MovieCredits> {
        return try {
            val response = apiService.getMovieCredits(id.toInt(), Constants.API_KEY)
            val result = response.body()
            if(response.isSuccessful&&result!=null){
                Resource.Success(result)
            }else Resource.Error("Unknown error occurred")
        }catch (e:Exception){
            Resource.Error("Network error occurred")
        }
    }

}