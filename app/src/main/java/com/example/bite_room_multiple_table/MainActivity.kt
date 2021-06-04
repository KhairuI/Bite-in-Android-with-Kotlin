package com.example.bite_room_multiple_table

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bite_room_multiple_table.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var dao: UserDAO
    private lateinit var adapter: MyAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dao= UserDB.getInstance(this).myDao
        binding.recycleView.layoutManager= LinearLayoutManager(this)
        binding.recycleView.setHasFixedSize(true)

        binding.addButton.setOnClickListener {
            startActivity(Intent(this,InsertActivity::class.java))
        }
        binding.searchButton.setOnClickListener {
            startActivity(Intent(this,SearchActivity::class.java))
        }
        val list= dao.getAllStudent()
       // Log.d("all_data", "onCreate: $list")
        adapter= MyAdapter(list,this)
        binding.recycleView.adapter= adapter
    }
}