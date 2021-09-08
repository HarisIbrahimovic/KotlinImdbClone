package com.example.kotlinimdbclone.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(
    private val mainRepository: MainRepository
) :ViewModel(){

    private val loginState = MutableLiveData<Int>()
    lateinit var fUser:LiveData<FirebaseUser?>

    val liveDataLoginState : LiveData<Int> = loginState
    var toastMessage:LiveData<String>?=null

    init {
        if(loginState.value==null&&toastMessage==null) {
            loginState.value = 1
            toastMessage=mainRepository.getToastMessage()
            fUser=mainRepository.getUser()
        }
    }

    fun loginUser(email:String,password:String,state:String){
        if(email.isEmpty()||password.isEmpty()){
            mainRepository.setToastMessage("Fill in the fields.")
            return
        }
        if(password.length<8){
            mainRepository.setToastMessage("Password too short.")
            return
        }

        if(state=="Login"){
            viewModelScope.launch(Dispatchers.IO) {
                mainRepository.loginUser(email,password)
            }
        }else{
            viewModelScope.launch(Dispatchers.IO) {
                mainRepository.signUpUser(email,password)
            }
        }
    }

    fun changeLoginState(){
        if(loginState.value==1)loginState.value=2
        else loginState.value=1
    }
}