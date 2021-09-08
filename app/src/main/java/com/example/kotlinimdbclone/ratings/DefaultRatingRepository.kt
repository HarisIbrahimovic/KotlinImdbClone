package com.example.kotlinimdbclone.ratings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.kotlinimdbclone.model.Rating
import com.example.kotlinimdbclone.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DefaultRatingRepository : RatingRepository{

    private val userData = MutableLiveData<User>()
    private val listOfRatings = MutableLiveData<List<Rating>>()

    override fun getRatings(id: String): LiveData<List<Rating>> {
        setList(id)
        return listOfRatings
    }

    override fun getUser(): LiveData<User> {
        userData.value= User()
        setUser()
        return userData
    }

    override fun submitRating(rating: Rating) {
        val databaseReference = FirebaseDatabase.getInstance().getReference("Ratings").push()
        databaseReference.setValue(rating)
    }

    private fun setList(id: String) {
        val dataBaseReference = FirebaseDatabase.getInstance().getReference("Ratings")
        dataBaseReference.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val arrayList = ArrayList<Rating>()
                for(dataSnapshot in snapshot.children){
                    val rating = dataSnapshot.getValue(Rating::class.java)
                    if(rating!!.id==id){
                        arrayList.add(rating)
                    }
                }
                listOfRatings.value=arrayList
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    private fun setUser() {
        if(FirebaseAuth.getInstance().currentUser==null)return
        val id = FirebaseAuth.getInstance().currentUser!!.uid
        val dataBaseReference = FirebaseDatabase.getInstance().getReference("Users").child(id)
        dataBaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)
                userData.value=user!!
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })

    }

}