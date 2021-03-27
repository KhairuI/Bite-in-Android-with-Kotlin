package com.example.bite_kotlin_views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            myText.text= "Bite in Android"
        }

        toastButton.setOnClickListener {
           Toast.makeText(this,"Bite in Android",Toast.LENGTH_LONG).show()
        }

        submitButton.setOnClickListener {
            if(editText.text.isEmpty()){
                editText.setError("Please enter name")
            }
            else
            {
                val value= editText.text.toString()
                Toast.makeText(this,value,Toast.LENGTH_LONG).show()
                editText.text.clear()
            }
        }

        activityButton.setOnClickListener {
            val change= Intent(this,SecondActivity::class.java)
            startActivity(change)
        }
    }
}