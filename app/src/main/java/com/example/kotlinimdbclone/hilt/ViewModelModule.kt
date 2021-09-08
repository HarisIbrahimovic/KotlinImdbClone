package com.example.kotlinimdbclone.hilt

import com.example.kotlinimdbclone.actordetails.ActorRepository
import com.example.kotlinimdbclone.actordetails.DefaultActorRepository
import com.example.kotlinimdbclone.apiservice.ApiService
import com.example.kotlinimdbclone.main.DefaultMainRepository
import com.example.kotlinimdbclone.main.MainRepository
import com.example.kotlinimdbclone.menu.DefaultMenuRepository
import com.example.kotlinimdbclone.menu.MenuRepository
import com.example.kotlinimdbclone.moviedetails.DefaultDetailsRepository
import com.example.kotlinimdbclone.moviedetails.DetailsRepository
import com.example.kotlinimdbclone.ratings.DefaultRatingRepository
import com.example.kotlinimdbclone.ratings.RatingRepository
import com.example.kotlinimdbclone.search.DefaultSearchRepository
import com.example.kotlinimdbclone.search.SearchRepository
import com.example.kotlinimdbclone.util.Constants
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {


    @Provides
    fun provideMainRepository(firebaseAuth: FirebaseAuth): MainRepository = DefaultMainRepository(firebaseAuth)

    @Provides
    fun provideMenuRepository(apiService: ApiService): MenuRepository = DefaultMenuRepository(apiService)

    @Provides
    fun provideRatingRepository():RatingRepository = DefaultRatingRepository()

    @Provides
    fun provideDetailsRepository(apiService: ApiService): DetailsRepository = DefaultDetailsRepository(apiService)


    @Provides
    fun provideActorRepository(apiService: ApiService): ActorRepository = DefaultActorRepository(apiService)

    @Provides
    fun provideSearchRepository(apiService: ApiService): SearchRepository = DefaultSearchRepository(apiService)

    @Provides
    fun provideRetrofitInstance(): ApiService = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)
}