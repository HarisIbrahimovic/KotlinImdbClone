package com.example.kotlinimdbclone.search

import com.example.kotlinimdbclone.model.Movies
import com.example.kotlinimdbclone.util.Resource

interface SearchRepository {
    suspend fun getSearchData(value:String): Resource<Movies>
}