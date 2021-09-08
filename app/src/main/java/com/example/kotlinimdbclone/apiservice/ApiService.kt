package com.example.kotlinimdbclone.apiservice

import com.example.kotlinimdbclone.model.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("/3/movie/{key_word}")
    suspend fun getMovies(
        @Path("key_word") key_word: String,
        @Query("api_key") api_key: String
    ):Response<Movies>

    @GET("/3/movie/{movieId}/credits")
    suspend fun getMovieCredits(
        @Path("movieId") movieId: Int,
        @Query("api_key") api_key: String
    ):Response<MovieCredits>

    @GET("/3/search/movie")
    suspend fun getSearchData(
        @Query("api_key") api_key: String,
        @Query("query") query: String
    ):Response<Movies>

    @GET("/3/person/{person_id}")
    suspend fun getActorDetails(
        @Path("person_id") person_id: Int,
        @Query("api_key") api_key: String
    ):Response<Actor>

    @GET("/3/person/{actorId}/movie_credits")
    suspend fun getActorCredits(
        @Path("actorId") actorId: Int,
        @Query("api_key") api_key: String
    ):Response<ActorCredits>

    @GET("/3/movie/{movieId}")
    suspend fun getSingleMovie(
        @Path("movieId") id: String,
        @Query("api_key") api_key: String,
    ): Response<MovieDetails>
}