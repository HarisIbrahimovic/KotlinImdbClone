package com.example.kotlinimdbclone.actordetails

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.kotlinimdbclone.adapters.CreditAdapter
import com.example.kotlinimdbclone.databinding.ActivityActorBinding
import com.example.kotlinimdbclone.moviedetails.DetailsActivity
import com.example.kotlinimdbclone.util.Constants
import com.example.kotlinimdbclone.util.IncomingActorCredits
import com.example.kotlinimdbclone.util.IncomingActorDetails
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActorActivity : AppCompatActivity(),CreditAdapter.OnClickCreditAdapter {
    private lateinit var binding: ActivityActorBinding
    private val viewModel: ActorViewModel by viewModels()
    private lateinit var creditAdapter:CreditAdapter
    private var actorProfileString= ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityActorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val id = intent.getStringExtra("id")
        viewModel.initViewModel(id!!.toInt())
        setUpRecView()
        observe()

        binding.addActorToFav.setOnClickListener {
            try{
                if(actorProfileString!=""){
                    if(viewModel.addToList(id,binding.actorNameDetails.text.toString(),actorProfileString))
                        Toast.makeText(applicationContext,"Added",Toast.LENGTH_SHORT).show()
                    else  Toast.makeText(applicationContext,"Problems..",Toast.LENGTH_SHORT).show()
                }
            }catch (e:Exception){
                Toast.makeText(applicationContext,"No data for actor..",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observe() {
        viewModel.liveDataActorDetails.observe(this,{actorEvent->
            when(actorEvent){
                is IncomingActorDetails.Success->{
                    binding.progressBar.visibility= View.GONE
                    val actorDetails = actorEvent.actor
                    binding.actorNameDetails.text = actorDetails.name
                    binding.actorBiographyDetails.text = actorDetails.biography
                    binding.actorDetailsPlaceOfBirth.text = actorDetails.place_of_birth
                    val requestOptions = RequestOptions.centerCropTransform()
                    if(actorDetails.profile_path.isNullOrEmpty()){
                        Glide.with(applicationContext)
                            .load(Constants.NO_IMAGE_USER)
                            .apply(requestOptions)
                            .into(binding.actorImageDetails)
                    }else
                        Glide.with(applicationContext)
                            .load(Constants.URL_START+actorDetails.profile_path)
                            .apply(requestOptions)
                            .into(binding.actorImageDetails)
                    actorProfileString= actorDetails.profile_path.toString()
                }
                is IncomingActorDetails.Failure->{
                    binding.progressBar.visibility= View.GONE
                    Toast.makeText(applicationContext,actorEvent.errorMessage,Toast.LENGTH_SHORT).show()
                }
                is IncomingActorDetails.Loading-> binding.progressBar.visibility= View.VISIBLE
                else -> Unit
            }
        })

        viewModel.liveDataActorCredits.observe(this,{actorCredits->
            when(actorCredits){
                is IncomingActorCredits.Success->{
                    creditAdapter.setList(actorCredits.actorCredits)
                }
                is IncomingActorCredits.Failure->{
                    binding.progressBar.visibility= View.GONE
                    Toast.makeText(applicationContext,actorCredits.errorMessage,Toast.LENGTH_LONG).show()
                }
                is IncomingActorCredits.Loading-> binding.progressBar.visibility= View.VISIBLE
                else -> Unit
            }

        })
    }

    private fun setUpRecView() {
        creditAdapter = CreditAdapter(applicationContext,this)
        binding.creditRecView.apply {
            layoutManager = LinearLayoutManager(applicationContext,LinearLayoutManager.HORIZONTAL,false)
            adapter=creditAdapter
        }
    }

    override fun onCreditClicked(id: String) {
        val intent = Intent(applicationContext, DetailsActivity::class.java)
        intent.putExtra("id", id)
        startActivity(intent)
    }
}