package com.example.kotlinimdbclone.menu.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinimdbclone.R
import com.example.kotlinimdbclone.adapters.MovieAdapter
import com.example.kotlinimdbclone.adapters.UpcomingMovieAdapter
import com.example.kotlinimdbclone.databinding.FragmentSearchBinding
import com.example.kotlinimdbclone.menu.MenuViewModel
import com.example.kotlinimdbclone.moviedetails.DetailsActivity
import com.example.kotlinimdbclone.util.IncomingMovieData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search),MovieAdapter.OnClickMovieAdapter,UpcomingMovieAdapter.OnClickUpcomingAdapter {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MenuViewModel by activityViewModels()
    private lateinit var popularAdapter: MovieAdapter
    private lateinit var nowPlayingAdapter: UpcomingMovieAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.initSearchFragment()
        setUpRecViews()
        observe()
    }

    private fun observe() {
        viewModel.liveDataTopRatedMovies.observe(viewLifecycleOwner,{ response->
            when(response){
                is IncomingMovieData.Loading-> binding.progressBar.visibility = View.VISIBLE
                is IncomingMovieData.Success -> {
                    binding.progressBar.visibility = View.GONE
                    popularAdapter.setList(response.movieData)
                }
                is IncomingMovieData.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), response.errorMessage, Toast.LENGTH_SHORT).show()
                }
                else->Unit
            }
        })

        viewModel.liveDataUpcomingMovies.observe(viewLifecycleOwner, { response ->
            when (response) {
                is IncomingMovieData.Loading -> binding.progressBar.visibility = View.VISIBLE
                is IncomingMovieData.Success -> {
                    binding.progressBar.visibility = View.GONE
                    nowPlayingAdapter.setList(response.movieData)
                }
                is IncomingMovieData.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), response.errorMessage, Toast.LENGTH_SHORT).show()
                }
                else -> Unit
            }
        })
    }

    private fun setUpRecViews() {
        popularAdapter= MovieAdapter(10,requireActivity().applicationContext,this)
        nowPlayingAdapter= UpcomingMovieAdapter(requireActivity().applicationContext,this)
        binding.topRatedRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL,false)
            adapter=popularAdapter
        }
        binding.upcomingRecView.apply {
            layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL,false)
            adapter=nowPlayingAdapter
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

    override fun onClickMovie(id: String) {
        val intent = Intent(requireContext(),DetailsActivity::class.java)
        intent.putExtra("id", id)
        startActivity(intent)
    }

    override fun onClickUpcoming(id: String) {
        val intent = Intent(requireContext(),DetailsActivity::class.java)
        intent.putExtra("id", id)
        startActivity(intent)
    }


}