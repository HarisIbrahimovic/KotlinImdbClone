package com.example.kotlinimdbclone.actordetails

import com.example.kotlinimdbclone.apiservice.ApiService
import com.example.kotlinimdbclone.model.Actor
import com.example.kotlinimdbclone.model.ActorCredits
import com.example.kotlinimdbclone.model.FavActor
import com.example.kotlinimdbclone.util.Constants
import com.example.kotlinimdbclone.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject

class DefaultActorRepository
@Inject
constructor(private val apiService: ApiService) :ActorRepository {

    override suspend fun getActorDetails(id: Int): Resource<Actor> {
        return try{
            val response = apiService.getActorDetails(id, Constants.API_KEY)
            val result = response.body()
            if(response.isSuccessful&&result!=null){
                Resource.Success(result)
            }else Resource.Error("Unknown error occured")
        }
        catch (e:Exception){
            Resource.Error("Network error occurred")
        }
        }

    override suspend fun getActorCredits(id: Int): Resource<ActorCredits> {
        return try{
            val response = apiService.getActorCredits(id, Constants.API_KEY)
            val result = response.body()
            if(response.isSuccessful&&result!=null){
                Resource.Success(result)
            }else Resource.Error("Unknown error occured")
        }
        catch (e:Exception){
            Resource.Error("Network error occurred $e")
        }
    }

    override fun addToFavorites(id: String, actorName: String, actorProfileString: String) {
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        val databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userId).child("Favorites").push()
        val favActor = FavActor(id,actorName,actorProfileString)
        databaseReference.setValue(favActor)
    }

}