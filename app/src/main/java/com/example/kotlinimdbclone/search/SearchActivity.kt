package com.example.kotlinimdbclone.search

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinimdbclone.adapters.SearchAdapter
import com.example.kotlinimdbclone.databinding.ActivitySearchBinding
import com.example.kotlinimdbclone.moviedetails.DetailsActivity
import com.example.kotlinimdbclone.util.IncomingMovieData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : AppCompatActivity(),SearchAdapter.OnClickSearchAdapter {

    private val viewModel: SearchViewModel by viewModels()
    private lateinit var binding: ActivitySearchBinding
    private lateinit var searchAdapter: SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpRecView()
        observe()
    }

    private fun observe() {
        viewModel.liveSearchData.observe(this,{searchDataEvent->
            when(searchDataEvent){
                is IncomingMovieData.Success-> {
                    binding.progressBar.visibility= View.GONE
                    searchAdapter.setList(searchDataEvent.movieData)
                }
                is IncomingMovieData.Failure->{
                    binding.progressBar.visibility= View.GONE
                    Toast.makeText(applicationContext,searchDataEvent.errorMessage,Toast.LENGTH_SHORT).show()
                }
                is IncomingMovieData.Loading->{
                    binding.progressBar.visibility= View.VISIBLE
                }
                else->Unit
            }
        })
    }

    private fun setUpRecView() {
        binding.searchBar.setOnEditorActionListener(editorActionListener)
        viewModel.getSearchData(intent.getStringExtra("title")!!)
        searchAdapter = SearchAdapter(applicationContext,this)
        binding.searchRecView.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = searchAdapter
        }
    }

    private val editorActionListener = OnEditorActionListener { _, _, _ ->
            val title: String = binding.searchBar.text.toString()
            if (!TextUtils.isEmpty(title)) {
                viewModel.getSearchData(title)
            } else Toast.makeText(applicationContext, "Fill in the fields", Toast.LENGTH_SHORT).show()
            true
        }

    override fun onClickSearch(id: String) {
        val intent = Intent(applicationContext,DetailsActivity::class.java)
        intent.putExtra("id", id)
        startActivity(intent)
    }

}