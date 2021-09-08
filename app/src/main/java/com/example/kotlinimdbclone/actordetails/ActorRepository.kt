package com.example.kotlinimdbclone.actordetails

import com.example.kotlinimdbclone.model.Actor
import com.example.kotlinimdbclone.model.ActorCredits
import com.example.kotlinimdbclone.util.Resource

interface ActorRepository {
    suspend fun getActorDetails(id:Int):Resource<Actor>
    suspend fun getActorCredits(id:Int):Resource<ActorCredits>
    fun addToFavorites(id: String, actorName: String, actorProfileString: String)
}