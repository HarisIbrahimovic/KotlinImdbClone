package com.example.kotlinimdbclone.ratings

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.kotlinimdbclone.model.Rating
import com.example.kotlinimdbclone.model.User
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class RatingViewModel
@Inject
constructor(private val ratingRepository: RatingRepository):ViewModel() {

    lateinit var listOfRatings :LiveData<List<Rating>>
    var userData: LiveData<User> = ratingRepository.getUser()

    fun initViewModel(id:String){
        listOfRatings= ratingRepository.getRatings(id)
    }

    fun submitRating(
        id:String,
        profilePath:String,
        moviePicPath:String,
        userName:String,
        movieName: String,
        comment: String,
        score:Float
    ):Boolean{
        if(FirebaseAuth.getInstance().currentUser==null)return false
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        val rating = Rating(id,userId,userName,profilePath,score,comment,movieName,moviePicPath)
        ratingRepository.submitRating(rating)
        return true
    }

}