package com.example.kotlinimdbclone.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.kotlinimdbclone.R
import com.example.kotlinimdbclone.model.Movies
import com.example.kotlinimdbclone.model.Result
import com.example.kotlinimdbclone.util.Constants

class UpcomingMovieAdapter(private val context:Context,private val onClickUpcomingAdapter: OnClickUpcomingAdapter): RecyclerView.Adapter<UpcomingMovieAdapter.ViewHolder>() {

    private var movieList: List<Result>?=null

    inner class ViewHolder(view:View) : RecyclerView.ViewHolder(view), View.OnClickListener{
        val movieImage: ImageView = view.findViewById(R.id.smallMoviePicture)
        init {
            view.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            onClickUpcomingAdapter.onClickUpcoming(movieList!![adapterPosition].id.toString())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.now_playing_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movieList!![position]
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
        return if(movieList==null)0
        else movieList!!.size
    }
    fun setList(list: Movies){
        movieList=list.results
        notifyDataSetChanged()
    }

    interface OnClickUpcomingAdapter{
        fun onClickUpcoming(id : String)
    }
}


