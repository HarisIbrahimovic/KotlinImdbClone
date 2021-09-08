package com.example.kotlinimdbclone.ratings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.kotlinimdbclone.model.Rating
import com.example.kotlinimdbclone.model.User

class FakeRatingRepository:RatingRepository {

    val listOfData = MutableLiveData<List<Rating>>()
    val arrayList = ArrayList<Rating>()
    val user = MutableLiveData<User>()

    override fun getRatings(id: String): LiveData<List<Rating>> {
        listOfData.value=arrayList
        return listOfData
    }

    override fun submitRating(rating: Rating) {
        arrayList.add(rating)
    }

    override fun getUser(): LiveData<User> {
        val helperUser = User()
        user.value=helperUser
        return user
    }
}