package com.example.kotlinimdbclone.main

import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseUser

interface MainRepository {
    suspend fun loginUser(email:String,password:String)
    suspend fun signUpUser(email:String,password:String)
    fun addUser(email: String,password: String)
    fun getUser():LiveData<FirebaseUser?>
    fun getToastMessage():LiveData<String>
    fun setToastMessage(string: String)
}