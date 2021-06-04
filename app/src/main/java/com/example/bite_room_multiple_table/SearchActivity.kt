package com.example.bite_room_multiple_table

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bite_room_multiple_table.databinding.ActivityInsertBinding
import com.example.bite_room_multiple_table.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {
    private lateinit var dao: UserDAO
    private lateinit var adapter: MyAdapter

    lateinit var binding: ActivitySearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dao= UserDB.getInstance(this).myDao
        binding.searchRecycle.layoutManager= LinearLayoutManager(this)
        binding.searchRecycle.setHasFixedSize(true)
        setSpinner()

        binding.btnSearch.setOnClickListener {
            val value= binding.spinnerId.selectedItem.toString()
            val result= dao.getStudentWithDepartment(value)
           // Log.d("Kotlin", "onCreate: $result")
            val list= result.students
            adapter= MyAdapter(list,this)
            binding.searchRecycle.adapter= adapter
        }
    }

    private fun setSpinner() {
        val dept= resources.getStringArray(R.array.department)
        val adapter= ArrayAdapter(this,R.layout.custom_spinner,dept)
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown)
        binding.spinnerId.adapter= adapter
    }
}