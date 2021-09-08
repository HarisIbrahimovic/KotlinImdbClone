package com.example.kotlinimdbclone.model

data class ActorCredits(
    val cast: List<CastActor>,
    val crew: List<CrewActor>,
    val id: Int
)