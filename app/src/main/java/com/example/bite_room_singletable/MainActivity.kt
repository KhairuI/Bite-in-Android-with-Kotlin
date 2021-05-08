package com.example.bite_room_singletable

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bite_room_singletable.databinding.ActivityMainBinding
import com.example.bite_room_singletable.databinding.UpdateDialogueBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(),View.OnClickListener,
MyAdapter.OnItmClick{
    private lateinit var binding: ActivityMainBinding
    private lateinit var dao: UserDAO
    private var userList= ArrayList<User>()
    private lateinit var adapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dao= UserDB.getInstance(this).myDao
        binding.insertButton.setOnClickListener(this)
        binding.recycleView.layoutManager= LinearLayoutManager(this)
        binding.recycleView.setHasFixedSize(true)
        loadData()
        binding.swipLayout.setOnRefreshListener {
            userList.clear()
            userList= dao.getUser() as ArrayList<User>
            adapter= MyAdapter(userList,this)
            binding.recycleView.adapter= adapter
            binding.swipLayout.isRefreshing= false

        }
    }

    private fun loadData() {
       userList= dao.getUser() as ArrayList<User>
        adapter= MyAdapter(userList,this)
        binding.recycleView.adapter= adapter

    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.insertButton ->{
                startActivity(Intent(this,InsertActivity::class.java))
            }
        }
    }

    override fun onItemClick(position: Int) {
        Snackbar.make(binding.layoutMain,""+userList[position].name,
            Snackbar.LENGTH_LONG).show()
    }

    override fun onLongItemClick(position: Int) {

        val dialogue= AlertDialog.Builder(this)
        val  option= arrayOf("Update","Delete")
        dialogue.setTitle("Choose a option")
        dialogue.setItems(option){ dialogInterface: DialogInterface, i: Int ->

            val select= option[i]
            if(select=="Update"){
                updateData(position)
            }
            else{
                val id= userList[position].id
                val value= dao.delete(id)
                if(value>0){
                    userList.removeAt(position)
                    adapter.notifyItemRemoved(position)
                    Snackbar.make(binding.layoutMain,"Delete Successfully",Snackbar.LENGTH_SHORT).show()

                }
                else{
                    Snackbar.make(binding.layoutMain,"Delete Failed",Snackbar.LENGTH_SHORT).show()

                }

            }
        }
        val alert= dialogue.create().show()
    }

    private fun updateData(position: Int) {

        val dialog= AlertDialog.Builder(this)
        val view= UpdateDialogueBinding.inflate(LayoutInflater.from(this))
        dialog.setView(view.root).setTitle("Update").setCancelable(true)
            .setPositiveButton("Update"){ dialogInterface: DialogInterface, i: Int ->

                val id= userList[position].id
                val name= view.updateName.text.toString()
                val email= view.updateEmail.text.toString()
                val value= dao.update(id, name, email)
                if(value>0){
                    Snackbar.make(binding.layoutMain,"Update Successfully",Snackbar.LENGTH_SHORT).show()

                }
                else{
                    Snackbar.make(binding.layoutMain,"Update Filed",Snackbar.LENGTH_SHORT).show()

                }

            }.setNegativeButton("Close"){ dialogInterface: DialogInterface, i: Int ->

            }

        view.updateId.text= "Updating index no ${userList[position].id}"
        view.updateName.setText(userList[position].name)
        view.updateEmail.setText(userList[position].email)
        dialog.create().show()

    }
}