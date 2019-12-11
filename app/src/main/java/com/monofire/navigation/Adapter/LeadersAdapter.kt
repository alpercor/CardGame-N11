package com.monofire.navigation.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.monofire.navigation.Modal.Player
import com.monofire.navigation.R
import com.squareup.picasso.Picasso

class LeadersAdapter(private val leaderList: MutableList<Player>) :
    RecyclerView.Adapter<LeadersAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_leader, parent, false))
    }
    override fun getItemCount(): Int = leaderList.size
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        Picasso.get().load(leaderList[position].imgUrl).resize(200,200).noPlaceholder().into(holder.imgProfile)
        holder.txtName.text=leaderList[position].mail
        holder.txtScore.text= leaderList[position].topScore.toString()
    }
    class MyViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val imgProfile=itemview.findViewById<ImageView>(R.id.img_profile)
        val txtName=itemview.findViewById<TextView>(R.id.txt_player_name)
        val txtScore=itemview.findViewById<TextView>(R.id.txt_player_score)
    }


}