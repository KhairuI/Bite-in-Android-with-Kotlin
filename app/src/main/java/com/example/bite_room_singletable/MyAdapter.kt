package com.example.bite_room_singletable

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.bite_room_singletable.databinding.SingleItemBinding


class MyAdapter(private val userList:List<User>, private  val listener:OnItmClick)
    :RecyclerView.Adapter<MyAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view= SingleItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.name.text= userList[position].name
        holder.email.text= userList[position].email
    }

    override fun getItemCount() = userList.size

    inner class MyViewHolder(private val binding: SingleItemBinding):RecyclerView.ViewHolder(binding.root),
    View.OnClickListener,View.OnLongClickListener{

        val name: TextView= binding.singleName
        val email: TextView= binding.singleEmail

        init {
            binding.root.setOnClickListener(this)
            binding.root.setOnLongClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position:Int= adapterPosition
            if(position != RecyclerView.NO_POSITION){
                listener.onItemClick(position)
            }
        }

        override fun onLongClick(p0: View?): Boolean {
            val position:Int= adapterPosition
            if(position != RecyclerView.NO_POSITION){
                listener.onLongItemClick(position)
            }

            return false
        }
    }

    interface OnItmClick{

        fun onItemClick(position:Int)
        fun onLongItemClick(position:Int)
    }
}

