package com.example.kotlinimdbclone.actordetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinimdbclone.util.IncomingActorCredits
import com.example.kotlinimdbclone.util.IncomingActorDetails
import com.example.kotlinimdbclone.util.Resource
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActorViewModel@Inject constructor(private val actorRepository: ActorRepository):ViewModel() {

    private val actorCredits = MutableLiveData<IncomingActorCredits>()
    private val actorDetails = MutableLiveData<IncomingActorDetails>()

    val liveDataActorDetails : LiveData<IncomingActorDetails> = actorDetails
    val liveDataActorCredits : LiveData<IncomingActorCredits> = actorCredits

    fun initViewModel(id:Int){
        getActorCredits(id)
        getActorDetails(id)
    }

    private fun getActorDetails(id: Int)= viewModelScope.launch(Dispatchers.IO) {
        actorDetails.postValue(IncomingActorDetails.Loading)
        when(val response = actorRepository.getActorDetails(id)){
            is Resource.Success->actorDetails.postValue(IncomingActorDetails.Success(response.data!!))
            is Resource.Error->actorDetails.postValue(IncomingActorDetails.Failure(response.message!!))
        }
    }

    private fun getActorCredits(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        actorCredits.postValue(IncomingActorCredits.Loading)
        when(val response = actorRepository.getActorCredits(id)){
            is Resource.Success->actorCredits.postValue(IncomingActorCredits.Success(response.data!!))
            is Resource.Error->actorCredits.postValue(IncomingActorCredits.Failure(response.message!!))
        }
    }

    fun addToList(id: String, actorName: String, actorProfileString: String): Boolean {
        if(FirebaseAuth.getInstance().currentUser==null) return false
        actorRepository.addToFavorites(id,actorName,actorProfileString)
        return true

    }

}