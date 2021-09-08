package com.example.kotlinimdbclone.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinimdbclone.model.FavActor
import com.example.kotlinimdbclone.model.Rating
import com.example.kotlinimdbclone.model.User
import com.example.kotlinimdbclone.util.IncomingMovieData
import com.example.kotlinimdbclone.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel
@Inject
constructor(private val menuRepository: MenuRepository) :ViewModel() {

    private val fragmentNum = MutableLiveData<Int>()
    val liveDataFragmentNum = fragmentNum
    var userData: LiveData<User>

    private val popularMovies = MutableLiveData<IncomingMovieData>()
    private val topRatedMovies = MutableLiveData<IncomingMovieData>()
    private val upcomingMovies = MutableLiveData<IncomingMovieData>()
    private val nowPlayingMovies = MutableLiveData<IncomingMovieData>()

    val liveDataPopularMovies : LiveData<IncomingMovieData>  = popularMovies
    val liveDataTopRatedMovies : LiveData<IncomingMovieData> = topRatedMovies
    val liveDataUpcomingMovies : LiveData<IncomingMovieData> = upcomingMovies
    val liveDataNowPlayingMovies : LiveData<IncomingMovieData> = nowPlayingMovies

    var liveDataFavoriteActors : LiveData<List<FavActor>>
    var liveDataUserRatings : LiveData<List<Rating>>

    init {
        if(fragmentNum.value==null){
            fragmentNum.value=1
        }
        userData=menuRepository.getUser()
        liveDataFavoriteActors = menuRepository.getUserFavorites()
        liveDataUserRatings = menuRepository.getUserRatings()
    }

    fun initHomeFragment(){
        if(popularMovies.value==null&&nowPlayingMovies.value==null){
            getPopularMovies()
            getNowPlayingMovies()
        }
    }
    fun initSearchFragment(){
        if(topRatedMovies.value==null&&upcomingMovies.value==null){
            getTopRatedMovies()
            getUpcomingMovies()
        }
    }

    private fun getNowPlayingMovies() = viewModelScope.launch(Dispatchers.IO){
        nowPlayingMovies.postValue(IncomingMovieData.Loading)
        when(val response = menuRepository.getNowPlayingMovies()){
            is Resource.Success->nowPlayingMovies.postValue(IncomingMovieData.Success(response.data!!))
            is Resource.Error->nowPlayingMovies.postValue(IncomingMovieData.Failure(response.message!!))
        }
    }

    private fun getUpcomingMovies() = viewModelScope.launch(Dispatchers.IO){
        upcomingMovies.postValue(IncomingMovieData.Loading)
        when(val response = menuRepository.getUpcomingMovies()){
            is Resource.Success->upcomingMovies.postValue(IncomingMovieData.Success(response.data!!))
            is Resource.Error->upcomingMovies.postValue(IncomingMovieData.Failure(response.message!!))
        }
    }

    private fun getTopRatedMovies() = viewModelScope.launch(Dispatchers.IO){
        topRatedMovies.postValue(IncomingMovieData.Loading)
        when(val response = menuRepository.getTopRatedMovies()){
            is Resource.Success->topRatedMovies.postValue(IncomingMovieData.Success(response.data!!))
            is Resource.Error->topRatedMovies.postValue(IncomingMovieData.Failure(response.message!!))
        }
    }

    private fun getPopularMovies() = viewModelScope.launch(Dispatchers.IO){
        popularMovies.postValue(IncomingMovieData.Loading)
        when(val response = menuRepository.getPopularMovies()){
            is Resource.Success->popularMovies.postValue(IncomingMovieData.Success(response.data!!))
            is Resource.Error->popularMovies.postValue(IncomingMovieData.Failure(response.message!!))
        }
    }

    fun setFragmentNum(num:Int){
        fragmentNum.value=num
    }

    fun updateUser(username: String, email: String, password: String):Boolean {
        if(username.isEmpty()||email.isEmpty()||password.isEmpty()){
            return false
        }
        menuRepository.updateUser(username,email,password)
        return true
    }

    fun updatePicutre(imageUrl: String) {
        menuRepository.uploadPicture(imageUrl)
    }

}