package com.example.bite_grid_recycleview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)


    }

    fun goRecycle(view: View) {
        startActivity(Intent(this,Recycle::class.java))
    }

    fun goGrid(view: View) {
        startActivity(Intent(this,Grid::class.java))
    }
}