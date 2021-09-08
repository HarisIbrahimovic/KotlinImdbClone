package com.example.kotlinimdbclone.menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.example.kotlinimdbclone.R
import com.example.kotlinimdbclone.databinding.ActivityMenuBinding
import com.example.kotlinimdbclone.menu.fragments.HomeFragment
import com.example.kotlinimdbclone.menu.fragments.ProfileFragment
import com.example.kotlinimdbclone.menu.fragments.SearchFragment
import com.example.kotlinimdbclone.menu.fragments.SettingsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding
    private lateinit var fragment:Fragment

    private val viewModel: MenuViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpNavigationBar()
    }

    private fun setUpNavigationBar(){
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home->viewModel.setFragmentNum(1)
                R.id.Search->viewModel.setFragmentNum(2)
                R.id.Profile->viewModel.setFragmentNum(3)
                R.id.Settings->viewModel.setFragmentNum(4)
            }
            true
        }

        viewModel.liveDataFragmentNum.observe(this,{ num->
            fragment = when(num){
                4-> SettingsFragment()
                3-> ProfileFragment()
                2-> SearchFragment()
                else-> HomeFragment()
            }
            supportFragmentManager.beginTransaction().replace(R.id.frame,fragment).commit()
        })
    }

}