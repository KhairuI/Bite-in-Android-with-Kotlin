package com.example.bite_room_multiple_table

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.example.bite_room_multiple_table.databinding.SingleItemBinding

import de.hdodenhof.circleimageview.CircleImageView


class MyAdapter(private val userList:List<Student>,val context:Context)
    :RecyclerView.Adapter<MyAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view= SingleItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.name.text= userList[position].name
        holder.email.text= userList[position].id
        holder.dept.text= userList[position].dept
        Glide.with(context).load(userList[position].image).into(holder.image)
    }

    override fun getItemCount() = userList.size

    inner class MyViewHolder(private val binding: SingleItemBinding):RecyclerView.ViewHolder(binding.root){

        val name: TextView= binding.singleName
        val email: TextView= binding.singleId
        val dept: TextView= binding.singleDept
        val image: CircleImageView= binding.circleImageView


    }

}

