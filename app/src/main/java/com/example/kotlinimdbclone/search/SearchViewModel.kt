package com.example.kotlinimdbclone.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinimdbclone.util.IncomingMovieData
import com.example.kotlinimdbclone.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel
@Inject
constructor(private val searchRepository: SearchRepository):ViewModel() {

    private val searchData = MutableLiveData<IncomingMovieData>()
    val liveSearchData : LiveData<IncomingMovieData> = searchData

    fun getSearchData(value:String)=viewModelScope.launch(Dispatchers.IO){
        searchData.postValue(IncomingMovieData.Loading)
        Log.d("Tag",Thread.currentThread().name+"HARIS MAJSTOR")
        when(val response = searchRepository.getSearchData(value)){
            is Resource.Success->searchData.postValue(IncomingMovieData.Success(response.data!!))
            is Resource.Error->searchData.postValue(IncomingMovieData.Failure(response.message!!))
        }
    }

}