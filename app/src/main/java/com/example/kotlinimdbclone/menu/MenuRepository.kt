package com.example.kotlinimdbclone.menu

import androidx.lifecycle.LiveData
import com.example.kotlinimdbclone.model.FavActor
import com.example.kotlinimdbclone.model.Movies
import com.example.kotlinimdbclone.model.Rating
import com.example.kotlinimdbclone.model.User
import com.example.kotlinimdbclone.util.Resource

interface MenuRepository {
    suspend fun getPopularMovies(): Resource<Movies>
    suspend fun getTopRatedMovies():Resource<Movies>
    suspend fun getUpcomingMovies():Resource<Movies>
    suspend fun getNowPlayingMovies():Resource<Movies>
    fun getUser():LiveData<User>
    fun getUserFavorites():LiveData<List<FavActor>>
    fun getUserRatings():LiveData<List<Rating>>
    fun uploadPicture(imageUrl: String)
    fun updateUser(username: String, email: String, password: String)
}