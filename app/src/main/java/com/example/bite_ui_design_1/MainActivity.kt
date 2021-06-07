package com.example.bite_ui_design_1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bite_ui_design_1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarId.toolbarTitle.text="Main Activity"
        binding.button1.setOnClickListener {
            startActivity(Intent(this,SecondActivity::class.java))
        }
    }
}