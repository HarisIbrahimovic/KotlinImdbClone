package com.example.kotlinimdbclone.search

import androidx.lifecycle.MutableLiveData
import com.example.kotlinimdbclone.model.Movies
import com.example.kotlinimdbclone.model.Result
import com.example.kotlinimdbclone.util.Resource
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

class FakeSearchRepository:SearchRepository {

    val movies = ArrayList<Movies>()
    val result = ArrayList<Result>()

    override suspend fun getSearchData(value: String): Resource<Movies> {
        return if(value==""){

            Resource.Error("No data")
        }else {
            movies.add(Movies(1,result,1,20))
            Resource.Success(movies[0])
        }
    }
}