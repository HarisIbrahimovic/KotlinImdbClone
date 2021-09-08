package com.example.kotlinimdbclone.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.kotlinimdbclone.R
import com.example.kotlinimdbclone.model.Movies
import com.example.kotlinimdbclone.model.Result
import com.example.kotlinimdbclone.util.Constants

class SearchAdapter(private val context:Context,private val onClickSearchAdapter: OnClickSearchAdapter): RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    private var searchList:List<Result>?=null

    inner class ViewHolder(view:View): RecyclerView.ViewHolder(view), View.OnClickListener {
        val movieImage: ImageView = view.findViewById(R.id.searchMovieImage)
        val movieName : TextView = view.findViewById(R.id.searchMovieName)
        val movieOriginalTitle : TextView = view.findViewById(R.id.SearchMovieOrignalTitle)
        val moviePopularity:TextView = view.findViewById(R.id.searchMoviePopularity)
        val movieDescription : TextView = view.findViewById(R.id.searchMovieReleaseDate)
        val movieReleaseDate : TextView = view.findViewById(R.id.searchMovieOverview)
        val movieRating : Button = view.findViewById(R.id.searchScoreButton)
        init {
            view.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            onClickSearchAdapter.onClickSearch(searchList!![adapterPosition].id.toString())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.search_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = searchList!![position]
        holder.movieName.text = movie.title
        holder.movieOriginalTitle.text = movie.original_title
        holder.moviePopularity.text = movie.popularity.toInt().toString()
        holder.movieDescription.text = movie.overview
        holder.movieReleaseDate.text = movie.release_date
        holder.movieRating.text = movie.vote_average.toString()
        val requestOptions = RequestOptions.centerCropTransform()

        if(movie.poster_path.isNullOrEmpty())
            Glide.with(context)
                .load(Constants.NO_IMAGE_MOVIE)
                .apply(requestOptions)
                .into(holder.movieImage)
        else
            Glide.with(context)
                .load(Constants.URL_START + movie.poster_path)
                .apply(requestOptions)
                .into(holder.movieImage)
    }

    override fun getItemCount(): Int {
        return if(searchList==null)0
        else searchList!!.size
    }

    fun setList(list: Movies){
        searchList=list.results
        notifyDataSetChanged()
    }

    interface OnClickSearchAdapter{
        fun onClickSearch(id: String)
    }
}