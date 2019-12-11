package com.cardGame.n11.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.cardGame.n11.Modal.Card
import com.cardGame.n11.Listener.CustomListener
import com.cardGame.n11.R


class CardAdapter(private val cardList: MutableList<Card>, val customItemListener: CustomListener) :
    RecyclerView.Adapter<CardAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false)

        val myViewHolder = MyViewHolder(v)
        v.setOnClickListener {
           // v.tag=cardList[myViewHolder.adapterPosition].id
            customItemListener.onItemClick(cardList[myViewHolder.adapterPosition], myViewHolder.adapterPosition,v)


        }
        return myViewHolder
    }

    override fun getItemCount(): Int = cardList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        //holder.image.setImageResource(cardList[position].name)
    }

    class MyViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val image: ImageView = itemview.findViewById(R.id.img_card)

    }




}



