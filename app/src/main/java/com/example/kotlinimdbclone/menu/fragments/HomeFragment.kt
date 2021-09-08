package com.example.kotlinimdbclone.menu.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.kotlinimdbclone.R
import com.example.kotlinimdbclone.adapters.MovieAdapter
import com.example.kotlinimdbclone.adapters.UpcomingMovieAdapter
import com.example.kotlinimdbclone.databinding.FragmentHomeBinding
import com.example.kotlinimdbclone.menu.MenuViewModel
import com.example.kotlinimdbclone.moviedetails.DetailsActivity
import com.example.kotlinimdbclone.search.SearchActivity
import com.example.kotlinimdbclone.util.Constants
import com.example.kotlinimdbclone.util.IncomingMovieData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home),MovieAdapter.OnClickMovieAdapter,UpcomingMovieAdapter.OnClickUpcomingAdapter {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MenuViewModel by activityViewModels()
    private lateinit var popularAdapter:MovieAdapter
    private lateinit var nowPlayingAdapter:UpcomingMovieAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.initHomeFragment()
        setUpView()
        setUpRecViews()
        observe()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

    private fun setUpView() {
        binding.searchBar.setOnEditorActionListener(editorActionListener)
        val requestOptions = RequestOptions.centerCropTransform()
        Glide.with(requireContext()).load(Constants.MOONLIGHT)
            .apply(requestOptions)
            .into(binding.posterPic)
        binding.openTrailer.setOnClickListener {
            val url = "https://www.youtube.com/watch?v=9NJj12tJzqc&ab_channel=A24"
            val i = Intent(Intent.ACTION_VIEW)
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            i.data = Uri.parse(url)
            startActivity(i)
        }
    }

    private fun observe() {
        viewModel.liveDataPopularMovies.observe(viewLifecycleOwner, { response ->
            when (response) {
                is IncomingMovieData.Loading -> binding.progressBar.visibility = View.VISIBLE
                is IncomingMovieData.Success -> {
                    binding.progressBar.visibility = View.GONE
                    popularAdapter.setList(response.movieData)
                }
                is IncomingMovieData.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), response.errorMessage, Toast.LENGTH_SHORT)
                        .show()
                }
                else -> Unit
            }
        })

        viewModel.liveDataNowPlayingMovies.observe(viewLifecycleOwner, { response ->
            when (response) {
                is IncomingMovieData.Loading -> binding.progressBar.visibility = View.VISIBLE
                is IncomingMovieData.Success -> {
                    binding.progressBar.visibility = View.GONE
                    nowPlayingAdapter.setList(response.movieData)
                }
                is IncomingMovieData.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), response.errorMessage, Toast.LENGTH_SHORT)
                        .show()
                }
                else -> Unit
            }
        })
    }

    private fun setUpRecViews() {
        popularAdapter= MovieAdapter(1, requireActivity().applicationContext, this)
        nowPlayingAdapter= UpcomingMovieAdapter(requireActivity().applicationContext, this)

        binding.recViewPopularMovies.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter=popularAdapter
        }

        binding.recyclerViewNowPlaying.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter=nowPlayingAdapter
        }
    }

    private val editorActionListener = TextView.OnEditorActionListener { _, _, _ ->
        val title: String = binding.searchBar.text.toString()
        val intent = Intent(requireContext(), SearchActivity::class.java)
        intent.putExtra("title", title)
        if (!TextUtils.isEmpty(title)) {
            binding.searchBar.setText("")
            startActivity(intent)
        } else Toast.makeText(requireContext(), "Fill in the field", Toast.LENGTH_SHORT).show()
        true
    }

    override fun onClickMovie(id: String) {
        val intent = Intent(requireContext(), DetailsActivity::class.java)
        intent.putExtra("id", id)
        startActivity(intent)
    }

    override fun onClickUpcoming(id: String) {
        val intent = Intent(requireContext(), DetailsActivity::class.java)
        intent.putExtra("id", id)
        startActivity(intent)
    }


}