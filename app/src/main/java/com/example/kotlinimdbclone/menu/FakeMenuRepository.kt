package com.example.kotlinimdbclone.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.kotlinimdbclone.model.FavActor
import com.example.kotlinimdbclone.model.Movies
import com.example.kotlinimdbclone.model.Rating
import com.example.kotlinimdbclone.model.User
import com.example.kotlinimdbclone.util.Resource

class FakeMenuRepository:MenuRepository {

    val movies : Movies? = null
    val listOfRatings = ArrayList<Rating>()
    val listOfFavorites = ArrayList<FavActor>()
    val cUser = User()
    val mUser = MutableLiveData<User>()
    val mListOfFavorites = MutableLiveData<List<FavActor>>()
    val mListOfRatings = MutableLiveData<List<Rating>>()



    override suspend fun getPopularMovies(): Resource<Movies> {
        return Resource.Success(movies!!)
    }

    override suspend fun getTopRatedMovies(): Resource<Movies> {
        return Resource.Success(movies!!)

    }

    override suspend fun getUpcomingMovies(): Resource<Movies> {
        return Resource.Success(movies!!)

    }

    override suspend fun getNowPlayingMovies(): Resource<Movies> {
        return Resource.Success(movies!!)

    }

    override fun getUser(): LiveData<User> {
        mUser.value=cUser
        return mUser
    }

    override fun getUserFavorites(): LiveData<List<FavActor>> {
        mListOfFavorites.value=listOfFavorites
        return mListOfFavorites
    }

    override fun getUserRatings(): LiveData<List<Rating>> {
        mListOfRatings.value= listOfRatings
        return mListOfRatings
    }

    override fun uploadPicture(imageUrl: String) {
    }

    override fun updateUser(username: String, email: String, password: String) {
        val user = User("null",email,username,"none",password)
        mUser.value=user
    }
}