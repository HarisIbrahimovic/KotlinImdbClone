package com.example.kotlinimdbclone.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject

class DefaultMainRepository
@Inject
constructor(
    private val firebaseAuth: FirebaseAuth
):MainRepository {

    private val fUser = MutableLiveData<FirebaseUser?>()
    private val toastMessage = MutableLiveData<String>()

    override fun getToastMessage(): LiveData<String> = toastMessage

    override fun setToastMessage(string: String) {
        toastMessage.value=string
    }

    override fun getUser(): LiveData<FirebaseUser?>  {
        fUser.value=firebaseAuth.currentUser
        return fUser
    }

    override suspend fun loginUser(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener {task->
            if(task.isSuccessful){
                addUser(email, password)
                fUser.value=firebaseAuth.currentUser
            }else
                toastMessage.value="Login failed."
        }
    }

    override suspend fun signUpUser(email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {task->
            if(task.isSuccessful){
                addUser(email, password)
                fUser.value=firebaseAuth.currentUser
            }else
                toastMessage.value="Sign up failed."
        }
    }

    override fun addUser(email: String, password: String) {
        val user = HashMap<String,String>()
        user["email"] = email
        user["password"] = password
        user["username"] = "user"
        user["profilePath"] = "default"
        user["id"] = firebaseAuth.currentUser!!.uid
        val databaseReference = FirebaseDatabase.getInstance().getReference("Users")
        databaseReference.child(firebaseAuth.currentUser!!.uid).setValue(user)
    }

}