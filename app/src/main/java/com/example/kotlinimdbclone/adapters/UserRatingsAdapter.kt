package com.example.kotlinimdbclone.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.kotlinimdbclone.R
import com.example.kotlinimdbclone.model.Rating
import com.example.kotlinimdbclone.util.Constants

class UserRatingsAdapter constructor(private val context: Context): RecyclerView.Adapter<UserRatingsAdapter.ViewHolder>() {

    private var listOfRatings : List<Rating>? = null

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val userName : TextView = view.findViewById(R.id.ratingName)
        val comment : TextView = view.findViewById(R.id.ratingComment)
        val score : TextView = view.findViewById(R.id.ratingScore)
        val imageView : ImageView = view.findViewById(R.id.ratingPicture)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.user_ratings_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val nR = "NR"
        val rating = listOfRatings!![position]
        holder.userName.text = rating.movieName
        holder.comment.text = rating.comment
        if(rating.score.toString()!="0.0") holder.score.text = rating.score.toString()
        else holder.score.text =nR

        val requestOptions = RequestOptions.centerCropTransform()

        if(rating.moviePicPath.isNullOrEmpty())
            Glide.with(context)
                .load(Constants.NO_IMAGE_MOVIE)
                .apply(requestOptions)
                .into(holder.imageView)
        else
            Glide.with(context).load(Constants.URL_START+rating.moviePicPath).into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return if(listOfRatings==null) 0
        else listOfRatings!!.size
    }

    fun setList(list:List<Rating>){
        listOfRatings = list
        notifyDataSetChanged()
    }
}