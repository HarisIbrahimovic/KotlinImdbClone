package com.example.kotlinimdbclone.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.kotlinimdbclone.apiservice.ApiService
import com.example.kotlinimdbclone.model.FavActor
import com.example.kotlinimdbclone.model.Movies
import com.example.kotlinimdbclone.model.Rating
import com.example.kotlinimdbclone.model.User
import com.example.kotlinimdbclone.util.Constants
import com.example.kotlinimdbclone.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.lang.Exception
import javax.inject.Inject

class DefaultMenuRepository
@Inject
constructor(private val apiService: ApiService):MenuRepository {

    private val userData = MutableLiveData<User>()
    private val listOfFavorites = MutableLiveData<List<FavActor>>()
    private val listOfRatings = MutableLiveData<List<Rating>>()

    override suspend fun getPopularMovies():Resource<Movies> {
        return try {
            val response = apiService.getMovies("popular", Constants.API_KEY)
            val result = response.body()
            if (response.isSuccessful && result != null) {
                Resource.Success(result)
            } else Resource.Error("Unexpected error occurred")
        }catch (e:Exception){
            Resource.Error("Network error occurred")
        }
    }

    override suspend fun getTopRatedMovies():Resource<Movies>  {
        return try {
            val response = apiService.getMovies("top_rated", Constants.API_KEY)
            val result = response.body()
            if (response.isSuccessful && result != null) {
                Resource.Success(result)
            } else Resource.Error("Unexpected error occurred")
        }catch (e:Exception){
            Resource.Error("Network error occurred")
        }
    }

    override suspend fun getUpcomingMovies():Resource<Movies>  {
        return try {
            val response = apiService.getMovies("upcoming", Constants.API_KEY)
            val result = response.body()
            if (response.isSuccessful && result != null) {
                Resource.Success(result)
            } else Resource.Error("Unexpected error occurred")
        }catch (e:Exception){
            Resource.Error("Network error occurred")
        }
    }

    override suspend fun getNowPlayingMovies() :Resource<Movies> {
        return try {
            val response = apiService.getMovies("now_playing", Constants.API_KEY)
            val result = response.body()
            if (response.isSuccessful && result != null) {
                Resource.Success(result)
            } else Resource.Error("Unexpected error occurred")
        }catch (e:Exception){
            Resource.Error("Network error occurred")
        }
    }

    override fun getUser(): LiveData<User> {
        userData.value=User()
        setUser()
        return userData
    }

    override fun getUserFavorites(): LiveData<List<FavActor>> {
        if(FirebaseAuth.getInstance().currentUser==null)return listOfFavorites
        val id = FirebaseAuth.getInstance().currentUser!!.uid
        val dataBaseReference = FirebaseDatabase.getInstance().getReference("Users").child(id).child("Favorites")
        dataBaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = ArrayList<FavActor>()
                for(dataSnapshot in snapshot.children){
                    val favActor = dataSnapshot.getValue(FavActor::class.java)
                    list.add(favActor!!)
                }
                listOfFavorites.value=list
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
        return listOfFavorites
    }

    override fun getUserRatings(): LiveData<List<Rating>> {
        if(FirebaseAuth.getInstance().currentUser==null)return listOfRatings
        val id = FirebaseAuth.getInstance().currentUser!!.uid
        val dataBaseReference = FirebaseDatabase.getInstance().getReference("Ratings")
        dataBaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = ArrayList<Rating>()
                for(dataSnapshot in snapshot.children){
                    val rating = dataSnapshot.getValue(Rating::class.java)
                    if(rating!!.userId==id)    list.add(rating)
                }
                listOfRatings.value=list
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
        return listOfRatings
    }

    override fun uploadPicture(imageUrl: String) {
        val dataBaseReference = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().currentUser!!.uid).child("profilePath")
        dataBaseReference.setValue(imageUrl)
    }

    override fun updateUser(username: String, email: String, password: String) {
        val dataBaseReference = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().currentUser!!.uid)
        dataBaseReference.child("username").setValue(username)
        dataBaseReference.child("email").setValue(email)
        dataBaseReference.child("password").setValue(password)
    }

    private fun setUser() {
        if(FirebaseAuth.getInstance().currentUser==null)return
        val id = FirebaseAuth.getInstance().currentUser!!.uid
        val dataBaseReference = FirebaseDatabase.getInstance().getReference("Users").child(id)
        dataBaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)
                userData.value=user!!
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })

    }

}