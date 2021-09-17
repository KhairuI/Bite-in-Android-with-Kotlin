package com.example.bitefirebase.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.bitefirebase.databinding.RowGalleryBinding
import com.example.bitefirebase.model.ModelImage

class GalleryUploadAdapter(private val listener:OnCardClickListener):RecyclerView.Adapter<GalleryUploadAdapter.MyViewHolder>() {

    private var imageList= mutableListOf<ModelImage>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view= RowGalleryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item= imageList[position]
        holder.apply {
            img.setImageURI(item.uri)
            imgCross.setOnClickListener {
                imageList.removeAt(position)
                notifyDataSetChanged()
                listener.onCardClick(position)
            }
        }
    }

    override fun getItemCount()= imageList.size

    class MyViewHolder(binding: RowGalleryBinding): RecyclerView.ViewHolder(binding.root){
        val imgCross: ImageView = binding.imgCancel
        val img: ImageView = binding.rowImg
    }

    fun addAll(elements:MutableList<ModelImage>,notify: Boolean){
        imageList.clear()
        imageList.addAll(elements)
        if(notify) notifyDataSetChanged()
    }

    interface OnCardClickListener {
        fun onCardClick(position: Int)
    }

}