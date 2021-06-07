package com.example.bite_ui_design_1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.bite_ui_design_1.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarSecondId.toolbarBackBtn.setImageResource(R.drawable.ic_back)
        binding.toolbarSecondId.settingIcon.setImageResource(R.drawable.ic_setting)

        binding.toolbarSecondId.toolbarTitle.text= "Second Activity"
        binding.toolbarSecondId.toolbarBackBtn.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }

        binding.toolbarSecondId.settingIcon.setOnClickListener {
            Toast.makeText(this,"Settings",Toast.LENGTH_LONG).show()
        }
    }
}