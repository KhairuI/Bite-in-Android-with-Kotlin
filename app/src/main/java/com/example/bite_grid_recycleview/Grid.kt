package com.example.bite_grid_recycleview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_grid.*
import kotlinx.android.synthetic.main.activity_recycle.*
import kotlin.random.Random

class Grid : AppCompatActivity() {

    private val image= arrayOf(R.drawable.messi,R.drawable.shakib,
        R.drawable.neymar,R.drawable.mushfiqur,R.drawable.tamim,R.drawable.bill,
        R.drawable.mark,R.drawable.jamal,R.drawable.siddikur,R.drawable.virat)

    private val playerList= ArrayList<Player>()
    private lateinit var adapter: GridAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grid)
        setTitle("Grid View")

        val playerName= resources.getStringArray(R.array.player)
        val playerRole= resources.getStringArray(R.array.role)

        for (i in playerName.indices){
            val player= Player(image[i],playerName[i],playerRole[i])
            playerList.add(player)
        }

        adapter= GridAdapter(playerList,this)
        gridView.adapter= adapter

        refreshGrid.setOnRefreshListener {
            val index= Random.nextInt(9)
            val player= Player(image[index],playerName[index],playerRole[index])
            playerList.add(player)
            adapter.notifyDataSetChanged()
            refreshGrid.isRefreshing= false
        }
    }
}