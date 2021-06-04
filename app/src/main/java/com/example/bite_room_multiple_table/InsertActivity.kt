package com.example.bite_room_multiple_table

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.bite_room_multiple_table.databinding.ActivityInsertBinding

class InsertActivity : AppCompatActivity(),View.OnClickListener {
    private var uri: Uri?= null
    lateinit var binding: ActivityInsertBinding
    private lateinit var dao: UserDAO
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityInsertBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dao= UserDB.getInstance(this).myDao

        setSpinner()
        binding.insertImage.setOnClickListener(this)
        binding.saveButton.setOnClickListener(this)
        binding.showButton.setOnClickListener(this)
    }

    private fun setSpinner() {
        val dept= resources.getStringArray(R.array.department)
        val adapter= ArrayAdapter(this,R.layout.custom_spinner,dept)
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown)
        binding.spinnerId.adapter= adapter
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.insertImage ->{

                openGallery()
            }
            R.id.saveButton ->{
                val name= binding.nameEditText.text.toString()
                val id= binding.idEditText.text.toString()
                val value= binding.spinnerId.selectedItem.toString()
                if(name.isNotEmpty() && id.isNotEmpty() && uri != null){
                    val student= Student(name,id,uri.toString(),value)
                    val dept= Department(value)
                    val studentDeptRelation= StudentDeptRelation(id,value)
                    val res1= dao.insertStudent(student)
                    val res2= dao.insertDept(dept)
                    val res3= dao.insertStudentDept(studentDeptRelation)

                    if(res1==(-1).toLong() || res2==(-1).toLong() ||res3==(-1).toLong()){
                        Toast.makeText(this,"Insert Failed", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Toast.makeText(this,"Insert Success", Toast.LENGTH_SHORT).show()
                    }
                }
                else{
                    Toast.makeText(this,"Enter all field", Toast.LENGTH_SHORT).show()
                }

            }
            R.id.showButton ->{

                startActivity(Intent(this,MainActivity::class.java))
            }
        }
    }

    private fun openGallery() {
        val intent= Intent(Intent.ACTION_PICK)
        intent.type= "image/*"
        startActivityForResult(intent,1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==1 && resultCode== RESULT_OK){
            uri= data?.data
            binding.insertImage.setImageURI(uri)
        }
    }
}