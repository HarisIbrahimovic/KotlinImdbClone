package com.example.kotlinimdbclone.menu.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.kotlinimdbclone.actordetails.ActorActivity
import com.example.kotlinimdbclone.adapters.FavoriteActorAdapter
import com.example.kotlinimdbclone.adapters.UserRatingsAdapter
import com.example.kotlinimdbclone.databinding.FragmentProfileBinding
import com.example.kotlinimdbclone.main.MainActivity
import com.example.kotlinimdbclone.menu.MenuViewModel
import com.example.kotlinimdbclone.util.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment(),FavoriteActorAdapter.OnClickActorAdapter {


    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel : MenuViewModel by activityViewModels()

    private lateinit var favoriteActorAdapter: FavoriteActorAdapter
    private lateinit var userRatingsAdapter: UserRatingsAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
        setUpRecView()
    }

    private fun observe() {
        viewModel.userData.observe(viewLifecycleOwner,{userData->
            if(userData.id=="null"){
                startActivity(Intent(requireContext(),MainActivity::class.java))
                requireActivity().finish()
            }else {
                binding.userName.text = userData.username
                val requestOptions = RequestOptions.centerCropTransform()
                if(userData.profilePath=="default"){
                    Glide.with(requireContext())
                        .load(Constants.NO_IMAGE_USER)
                        .apply(requestOptions)
                        .into(binding.userImage)
                }else
                    Glide.with(requireContext())
                        .load(userData.profilePath)
                        .apply(requestOptions)
                        .into(binding.userImage)
            }
        })

        viewModel.liveDataFavoriteActors.observe(viewLifecycleOwner,{list->
            favoriteActorAdapter.setList(list)
        })

        viewModel.liveDataUserRatings.observe(viewLifecycleOwner,{list->
            userRatingsAdapter.setList(list)
        })
    }

    private fun setUpRecView() {
        favoriteActorAdapter = FavoriteActorAdapter(requireContext(),this)
        userRatingsAdapter = UserRatingsAdapter(requireContext())
        binding.favActorsRec.apply {
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
            adapter=favoriteActorAdapter
        }

        binding.userRatingRec.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter=userRatingsAdapter
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

    override fun onActorClicked(id: String) {
        val intent = Intent(requireContext(), ActorActivity::class.java)
        intent.putExtra("id",id)
        startActivity(intent)
    }


}