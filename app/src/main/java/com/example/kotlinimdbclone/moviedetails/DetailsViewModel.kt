package com.example.kotlinimdbclone.moviedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinimdbclone.util.IncomingMovieCredits
import com.example.kotlinimdbclone.util.IncomingMovieDetails
import com.example.kotlinimdbclone.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel
@Inject
constructor(private val detailsRepository: DetailsRepository)
:ViewModel() {

    private val movieDetails = MutableLiveData<IncomingMovieDetails>()
    val liveDataMovieDetails : LiveData<IncomingMovieDetails> = movieDetails

    private val movieCredits = MutableLiveData<IncomingMovieCredits>()
    val liveDataMovieCredits : LiveData<IncomingMovieCredits> = movieCredits

    fun initViewModel(id:String){
        if(movieCredits.value!=null&&movieDetails.value!=null)
            return
        getDetails(id)
        getCredits(id)
    }

    private fun getDetails(id:String)= viewModelScope.launch(Dispatchers.IO){
        movieDetails.postValue(IncomingMovieDetails.Loading)
        when(val response = detailsRepository.getMovieDetails(id)){
            is Resource.Success -> movieDetails.postValue(IncomingMovieDetails.Success(response.data!!))
            is Resource.Error -> movieDetails.postValue(IncomingMovieDetails.Failure(response.message!!))
        }
    }

    private fun getCredits(id:String)= viewModelScope.launch(Dispatchers.IO){
        movieCredits.postValue(IncomingMovieCredits.Loading)
        when(val response = detailsRepository.getMovieCast(id)){
            is Resource.Success -> movieCredits.postValue(IncomingMovieCredits.Success(response.data!!))
            is Resource.Error -> movieCredits.postValue(IncomingMovieCredits.Failure(response.message!!))
        }
    }



}