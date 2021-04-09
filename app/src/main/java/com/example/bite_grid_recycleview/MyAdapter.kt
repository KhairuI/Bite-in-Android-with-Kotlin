package com.example.bite_grid_recycleview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.single_recycle_item.view.*

class MyAdapter(private val playerList:List<Player>) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
       val view= LayoutInflater.from(parent.context)
           .inflate(R.layout.single_recycle_item,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.image.setImageResource(playerList[position].image)
        holder.name.text= playerList[position].name
        holder.role.text= playerList[position].role
    }

    override fun getItemCount(): Int {
       return playerList.size
    }

   // override fun getItemCount()= playerList.size

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val image: CircleImageView= itemView.singleRecycleImage
        val name: TextView= itemView.singleRecycleName
        val role: TextView= itemView.singleRecycleRole
    }
}