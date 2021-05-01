package com.example.bite_kotlin_sqlite

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bite_kotlin_sqlite.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(),View.OnClickListener,MyAdapter.OnItemClick {

    lateinit var dbHelper: DBHelper
    lateinit var adapter: MyAdapter
    private var userList= ArrayList<User>()

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title= "User List"
        binding.insertButton.setOnClickListener(this)
        binding.logoutButton.setOnClickListener(this)
         binding.recycleId.layoutManager= LinearLayoutManager(this)
        binding.recycleId.setHasFixedSize(true)
        loadData()

    }

    private fun loadData() {
        dbHelper= DBHelper(this)
        val cursor= dbHelper.show()
        if(cursor.count==0){
            Snackbar.make(binding.mainLayout,"No data found",Snackbar.LENGTH_SHORT).show()
        }
        else{

            while (cursor.moveToNext()){
                val id= cursor.getString(0).toString()
                val name= cursor.getString(1).toString()
                val email= cursor.getString(2).toString()
                val user= User(id,name, email)
                userList.add(user)
            }
            adapter= MyAdapter(userList,this)
            binding.recycleId.adapter= adapter
        }
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

    override fun onItemClick(position: Int) {
     Snackbar.make(binding.mainLayout,""+userList[position].name,Snackbar.LENGTH_SHORT).show()
    }

    override fun onLongItemClick(position: Int) {
        val dialogue= AlertDialog.Builder(this)
        val  option= arrayOf("Update","Delete")
        dialogue.setTitle("Choose a option")
        dialogue.setItems(option){ dialogInterface: DialogInterface, i: Int ->

            val select= option[i]
            if(select=="Update"){
                Snackbar.make(binding.mainLayout,"Update",Snackbar.LENGTH_SHORT).show()
            }
            else{
                val id= userList[position].id
                val value= dbHelper.delete(id)
                if(value>0){
                    userList.removeAt(position)
                    adapter.notifyItemRemoved(position)
                    Snackbar.make(binding.mainLayout,"Delete Successfully",Snackbar.LENGTH_SHORT).show()

                }
                else{
                    Snackbar.make(binding.mainLayout,"Delete Failed",Snackbar.LENGTH_SHORT).show()

                }

            }
        }
        val alert= dialogue.create().show()


    }


// Constrain Layout..
// View Binding...
// Shared Preference Database.../ key -> value// name = Alex
// SQLite Database....
// Splash Screen....

}