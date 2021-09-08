package com.example.kotlinimdbclone.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.kotlinimdbclone.model.User
import com.google.firebase.auth.FirebaseUser

class FakeMainRepository:MainRepository {


    private val fUser = MutableLiveData<FirebaseUser?>()
    private val toastMessage = MutableLiveData<String>()
    val listOfUser = ArrayList<User>()

    override suspend fun loginUser(email: String, password: String) {
        for(user in listOfUser){
            if(user.email==email&&user.password==password){
                setToastMessage("Accepted.")
                break
            }
        }
    }

    override suspend fun signUpUser(email: String, password: String) {
        listOfUser.add(User("null",email,"name","pp",password))
    }

    override fun addUser(email: String, password: String) {
        listOfUser.add(User("null",email,"name","pp",password))
    }

    override fun setToastMessage(string: String) {
        toastMessage.value=string
    }

    override fun getUser(): LiveData<FirebaseUser?> = fUser
    override fun getToastMessage(): LiveData<String> = toastMessage
}