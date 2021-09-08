package com.example.kotlinimdbclone.model

class Rating(
    val id : String="null",
    val userId : String="null",
    val userName : String = "null",
    val userPicturePath : String = "default",
    val score : Float=0F,
    val comment : String="No comment",
    val movieName : String = "null",
    val moviePicPath : String? = "default"
)