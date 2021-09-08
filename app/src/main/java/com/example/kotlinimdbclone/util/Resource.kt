package com.example.kotlinimdbclone.util

import com.example.kotlinimdbclone.model.*


sealed class Resource<T>(val data:T?,val message:String?) {
    class Success<T>(data:T):Resource<T>(data,null)
    class Error<T>(message: String):Resource<T>(null,message)
}


sealed class IncomingMovieData{
    class Success(val movieData: Movies):IncomingMovieData()
    class Failure(val errorMessage: String):IncomingMovieData()
    object Loading : IncomingMovieData()
}


sealed class IncomingMovieDetails{
    class Success(val movieDetails: MovieDetails):IncomingMovieDetails()
    class Failure(val errorMessage: String):IncomingMovieDetails()
    object Loading : IncomingMovieDetails()
}

sealed class IncomingMovieCredits{
    class Success(val movieCredits: MovieCredits):IncomingMovieCredits()
    class Failure(val errorMessage: String):IncomingMovieCredits()
    object Loading : IncomingMovieCredits()
}

sealed class IncomingActorCredits{
    class Success(val actorCredits: ActorCredits):IncomingActorCredits()
    class Failure(val errorMessage: String):IncomingActorCredits()
    object Loading : IncomingActorCredits()
}

sealed class IncomingActorDetails{
    class Success(val actor: Actor):IncomingActorDetails()
    class Failure(val errorMessage: String):IncomingActorDetails()
    object Loading : IncomingActorDetails()
}