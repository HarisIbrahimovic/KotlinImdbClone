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
import com.example.kotlinimdbclone.model.*
import com.example.kotlinimdbclone.util.Constants


class CreditAdapter(private val context: Context, private val onClickCreditAdapter: OnClickCreditAdapter): RecyclerView.Adapter<CreditAdapter.ViewHolder>() {

    private var listOfCredits:List<CastActor>?=null

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener {
        val creditCharacter : TextView = view.findViewById(R.id.creditCharacter)
        val movieScore: Button = view.findViewById(R.id.scoreButtonSmall)
        val movieImage : ImageView = view.findViewById(R.id.moviePictureCredits)
        init {
            view.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            onClickCreditAdapter.onCreditClicked(listOfCredits!![adapterPosition].id.toString())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.credit_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val actor = listOfCredits!![position]
        holder.creditCharacter.text=actor.character
        holder.movieScore.text=actor.vote_average.toString()
        val requestOptions = RequestOptions.centerCropTransform()

        if(actor.poster_path.isNullOrEmpty()){
            Glide.with(context)
                .load(Constants.NO_IMAGE_USER)
                .apply(requestOptions)
                .into(holder.movieImage)
        }else
        Glide.with(context)
            .load(Constants.URL_START + actor.poster_path)
            .apply(requestOptions)
            .into(holder.movieImage)
    }

    override fun getItemCount(): Int {
        return if(listOfCredits==null)0
        else listOfCredits!!.size
    }

    fun setList(actorCredits: ActorCredits) {
        val list = actorCredits.cast
        listOfCredits=list
        notifyDataSetChanged()
    }

    interface OnClickCreditAdapter{
        fun onCreditClicked(id: String)
    }



}