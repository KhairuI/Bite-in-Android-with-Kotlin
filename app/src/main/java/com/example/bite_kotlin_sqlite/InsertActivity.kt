package com.example.bite_kotlin_sqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bite_kotlin_sqlite.databinding.ActivityInsertBinding
import com.google.android.material.snackbar.Snackbar

class InsertActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInsertBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityInsertBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val db= DBHelper(this)

        binding.saveButton.setOnClickListener {

            val name= binding.nameEditText.text.toString()
            val email= binding.emailEditText.text.toString()

            if(name.isNotEmpty() && email.isNotEmpty()){
                val value= db.insert(name, email)
                if(value== (-1).toLong()){
                    Snackbar.make(binding.insertLayout,"Insert Failed",Snackbar.LENGTH_SHORT).show()
                }
                else{
                    Snackbar.make(binding.insertLayout,"Insert Successfully",Snackbar.LENGTH_SHORT).show()

                }
            }
            else{
                Snackbar.make(binding.insertLayout,"Please Insert Data",Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}