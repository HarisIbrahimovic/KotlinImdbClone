package com.example.kotlinimdbclone.search

import com.example.kotlinimdbclone.apiservice.ApiService
import com.example.kotlinimdbclone.model.Movies
import com.example.kotlinimdbclone.util.Constants
import com.example.kotlinimdbclone.util.Resource
import javax.inject.Inject

class DefaultSearchRepository
@Inject
constructor(private val apiService: ApiService) : SearchRepository {

    override suspend fun getSearchData(value: String): Resource<Movies> {
        return try {
            val response = apiService.getSearchData(Constants.API_KEY,value)
            val result = response.body()
            if(response.isSuccessful&&result!=null){
                Resource.Success(result)
            }else Resource.Error("Unknown error occurred.")
        } catch (e:Exception){
            Resource.Error("Network error occurred.")
        }
    }

}