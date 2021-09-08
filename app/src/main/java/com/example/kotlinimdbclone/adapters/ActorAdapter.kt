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
import com.example.kotlinimdbclone.model.Cast
import com.example.kotlinimdbclone.model.MovieCredits
import com.example.kotlinimdbclone.util.Constants

class ActorAdapter(private val context:Context,private val onClickActorAdapter: OnClickActorAdapter): RecyclerView.Adapter<ActorAdapter.ViewHolder>() {

    private var listOfActors:List<Cast>?=null

    inner class ViewHolder(view:View):RecyclerView.ViewHolder(view),View.OnClickListener {
        val actorName : TextView = view.findViewById(R.id.actorNameItem)
        val actorRole : TextView = view.findViewById(R.id.actorRoleItem)
        val actorImage : ImageView = view.findViewById(R.id.actorImageItem)
        init {
            view.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            onClickActorAdapter.onActorClicked(listOfActors!![adapterPosition].id.toString())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.actor_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val actor = listOfActors!![position]
        holder.actorName.text=actor.name
        holder.actorRole.text=actor.character
        val requestOptions = RequestOptions.centerCropTransform()
        if(actor.profile_path.isNullOrEmpty()){
                Glide.with(context)
                    .load(Constants.NO_IMAGE_MOVIE)
                    .apply(requestOptions)
                    .into(holder.actorImage)
            }else Glide.with(context).load( Constants.URL_START + actor.profile_path).apply(requestOptions).into(holder.actorImage)

    }

    override fun getItemCount(): Int {
        return if(listOfActors==null)0
        else listOfActors!!.size
    }

    fun setList(movieCredits: MovieCredits) {
        val list = movieCredits.cast
        listOfActors=list
        notifyDataSetChanged()
    }

    interface OnClickActorAdapter{
        fun onActorClicked(id: String)
    }

}