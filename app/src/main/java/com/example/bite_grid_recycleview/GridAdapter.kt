package com.example.bite_grid_recycleview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class GridAdapter(private  val playerList:List<Player>,
                  private val context: Context): BaseAdapter() {

    var layoutInflater= context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    override fun getView(position: Int, view: View?, p2: ViewGroup?): View {
        var view= view
        if(view==null){
            view= layoutInflater.inflate(R.layout.single_grid_item,p2,false)
        }

        val image:ImageView?= view?.findViewById(R.id.gridSingleImage)
        val name:TextView?= view?.findViewById(R.id.gridSingleName)
        val role:TextView?= view?.findViewById(R.id.gridSingleRole)

        image?.setImageResource(playerList[position].image)
        name?.text=(playerList[position].name)
        role?.text=(playerList[position].role)


        return view!!
    }


    override fun getCount(): Int {
      return playerList.size
    }

    override fun getItem(p0: Int): Any {
       return playerList[p0]
    }

    override fun getItemId(p0: Int): Long {
       return p0.toLong()
    }


}