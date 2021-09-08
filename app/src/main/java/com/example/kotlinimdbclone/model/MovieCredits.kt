package com.example.kotlinimdbclone.model

data class MovieCredits(
    val cast: List<Cast>,
    val crew: List<Crew>,
    val id: Int
)