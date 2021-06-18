package com.example.bite_mvvm_sqlite.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bite_mvvm_sqlite.databinding.SingleItemBinding
import com.example.bite_mvvm_sqlite.model.Item
import com.example.bite_mvvm_sqlite.ui_view.MainViewModel

class MyAdapter(var itemList:MutableList<Item>,var searchList:MutableList<Item>,private val viewModel: MainViewModel)
    :RecyclerView.Adapter<MyAdapter.MyViewHolder>(),Filterable{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view= SingleItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val myItem= itemList[position]
        holder.name.text= myItem.name
        holder.amount.text= "${myItem.amount}"
        holder.delete.setOnClickListener {
            viewModel.delete(myItem)
        }
        holder.plus.setOnClickListener {
            myItem.amount++
            viewModel.insert(myItem)
        }

        holder.minus.setOnClickListener {
            if(myItem.amount>0){
                myItem.amount--
                viewModel.insert(myItem)

            }
        }
    }

    override fun getItemCount()= itemList.size

   inner class MyViewHolder(private val binding: SingleItemBinding):RecyclerView.ViewHolder(binding.root){
       val name: TextView = binding.singleName
       val amount: TextView = binding.singleAmount
       val delete: ImageView = binding.deleteBtn
       val plus: ImageView = binding.plusBtn
       val minus: ImageView = binding.minusBtn


   }

    override fun getFilter(): Filter {
       return itemFilter
    }

    // filter
    private val itemFilter:Filter= object :Filter(){
        override fun performFiltering(text: CharSequence): FilterResults {
            val filterItem: MutableList<Item> = mutableListOf()
            if(text.isEmpty()){
                filterItem.addAll(searchList)
            }
            else{
                val filterPattern= text.toString().lowercase().trim()
                for(item in searchList){
                    if(item.name.lowercase().contains(filterPattern)){
                        filterItem.add(item)
                    }
                }
            }
            val result= FilterResults()
            result.values= filterItem
            return result
        }

        override fun publishResults(p0: CharSequence?, result: FilterResults) {
            itemList.clear()
            itemList.addAll(result.values as Collection<Item>)
            notifyDataSetChanged()
        }

    }

}