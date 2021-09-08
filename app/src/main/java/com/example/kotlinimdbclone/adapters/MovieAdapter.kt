package com.example.kotlinimdbclone.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.kotlinimdbclone.R
import com.example.kotlinimdbclone.model.Movies
import com.example.kotlinimdbclone.model.Result
import com.example.kotlinimdbclone.util.Constants

class MovieAdapter(private val num: Int, private val context: Context,private val onClickMovieAdapter: OnClickMovieAdapter) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    private var listOfMovie : List<Result>? = null

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view),View.OnClickListener {
        val scoreButton:Button = view.findViewById(R.id.scoreButtonSmall)
        val movieImage: ImageView = view.findViewById(R.id.smallMoviePicture)
        init {
            view.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            onClickMovieAdapter.onClickMovie(listOfMovie!![adapterPosition].id.toString())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = when(num){
            1 -> LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
            else -> LayoutInflater.from(parent.context).inflate(
                R.layout.big_movie_item,
                parent,
                false
            )
        }
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = listOfMovie!![position]
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
        if(num==1) {
            val nR = "NR"
            if(movie.vote_average.toString()!="0.0") holder.scoreButton.text = movie.vote_average.toString()
            else holder.scoreButton.text =nR
        }
    }

    override fun getItemCount(): Int {
       return  if (listOfMovie==null)0
        else listOfMovie!!.size
    }

    fun setList(list: Movies){
        listOfMovie=list.results
        notifyDataSetChanged()
    }

    interface OnClickMovieAdapter{
        fun onClickMovie(id:String)
    }

}