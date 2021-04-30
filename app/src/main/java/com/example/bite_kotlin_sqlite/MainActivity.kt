package com.example.bite_kotlin_sqlite

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.bite_kotlin_sqlite.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(),View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title= "User List"
        binding.insertButton.setOnClickListener(this)
        binding.logoutButton.setOnClickListener(this)

    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.insertButton ->{
                startActivity(Intent(this,InsertActivity::class.java))
            }
            R.id.logoutButton ->{
                val sharedPreferences= getSharedPreferences("database",Context.MODE_PRIVATE)
                val edit= sharedPreferences.edit()
                edit.apply {
                    putString("status","notLogin")
                }.apply()

                val intent= Intent(this,LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            }
        }
    }


// Constrain Layout..
// View Binding...
// Shared Preference Database.../ key -> value// name = Alex
// SQLite Database....
// Splash Screen....

}