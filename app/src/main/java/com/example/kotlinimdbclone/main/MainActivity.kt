package com.example.kotlinimdbclone.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinimdbclone.R
import com.example.kotlinimdbclone.databinding.ActivityMainBinding
import com.example.kotlinimdbclone.menu.MenuActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private val viewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observe()
        onClicks()
    }

    private fun observe() {
        viewModel.liveDataLoginState.observe(this,{ value->
            if(value==1){
                binding.loginButton.text = getString(R.string.loginU)
                binding.currentStateTextMain.text = getString(R.string.loginU)
                binding.currentStateTextSecondary.text = getString(R.string.double_tap_login_to_signup)
            }else{
                binding.loginButton.text = getString(R.string.signUpU)
                binding.currentStateTextMain.text = getString(R.string.signUpU)
                binding.currentStateTextSecondary.text = getString(R.string.double_tap_signup_to_login)
            }
        })
        viewModel.toastMessage!!.observe(this,{ message->
            Toast.makeText(applicationContext,message,Toast.LENGTH_SHORT).show()
        })
        viewModel.fUser.observe(this,{user->
            if(user!=null){
                val intent = Intent(applicationContext,MenuActivity::class.java)
                startActivity(intent)
                finish()
            }
        })
    }

    private fun onClicks() {
        binding.continueOffline.setOnClickListener{
            val intent = Intent(applicationContext,MenuActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.loginButton.setOnClickListener{
            viewModel.loginUser(
                binding.loginEmail.text.toString(),
                binding.loginPassword.text.toString(),
                binding.loginButton.text.toString()
            )
        }
        binding.currentStateTextMain.setOnClickListener{
            viewModel.changeLoginState()
        }
        binding.continueOffline.setOnClickListener{
            val intent = Intent(applicationContext,MenuActivity::class.java)
            startActivity(intent)
        }
    }

}