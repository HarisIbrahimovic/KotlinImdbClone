package com.example.kotlinimdbclone.ratings

import androidx.lifecycle.LiveData
import com.example.kotlinimdbclone.model.Rating
import com.example.kotlinimdbclone.model.User

interface RatingRepository {
    fun getRatings(id:String):LiveData<List<Rating>>
    fun submitRating(rating: Rating)
    fun getUser(): LiveData<User>
}