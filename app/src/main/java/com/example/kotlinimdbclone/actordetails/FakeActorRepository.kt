package com.example.kotlinimdbclone.actordetails

import com.example.kotlinimdbclone.model.Actor
import com.example.kotlinimdbclone.model.ActorCredits
import com.example.kotlinimdbclone.model.FavActor
import com.example.kotlinimdbclone.util.Resource

class FakeActorRepository:ActorRepository {

    val actor : Actor? = null
    val actorCredits : ActorCredits? = null
    val listOfFavorites = ArrayList<FavActor>()

    override suspend fun getActorDetails(id: Int): Resource<Actor> {
        if(id==-1)return Resource.Error("No data")
        return Resource.Success(actor!!)
    }

    override suspend fun getActorCredits(id: Int): Resource<ActorCredits> {
        if(id==-1)return Resource.Error("No data")
        return Resource.Success(actorCredits!!)
    }

    override fun addToFavorites(id: String, actorName: String, actorProfileString: String) {
        val favActor  = FavActor(id,actorName,actorProfileString)
        listOfFavorites.add(favActor)
    }
}