package com.example.bite_kotlin_form

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import java.io.ByteArrayOutputStream
import java.sql.Date
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var uri: Uri?=null
    private var userTime:String?=null
    private var userDate:String?=null
    private var userBlood:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pickImage.setOnClickListener(this)
        pickDate.setOnClickListener(this)
        pickTime.setOnClickListener(this)
        submitButton.setOnClickListener(this)

        val bloodList= resources.getStringArray(R.array.blood_group)
        spinner.adapter= ArrayAdapter(this,android.R.layout.simple_list_item_1,bloodList)
        spinner.onItemSelectedListener= object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, i: Int, p3: Long) {
                userBlood= bloodList.get(i)
              pickBloodText.text= bloodList.get(i)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }

    }

    override fun onClick(view: View?) {

        when(view?.id){
            R.id.pickImage -> {
                openDialogue()
            }
            R.id.pickDate -> {
                takeDate()
            }

            R.id.pickTime -> {
                takeTime()
            }

            R.id.submitButton -> {
               /* val intent= Intent(this,ProfileActivity::class.java)
                intent.putExtra("name",pickName.text.toString())
                startActivity(intent)*/
                val userName= pickName.text.toString()
                val userEmail= pickEmail.text.toString()
                var userGender= ""
                var userSkill= ""
                when(radioGroup.checkedRadioButtonId){
                    R.id.male ->{
                        userGender= male.text.toString()
                    }
                    R.id.female ->{
                    userGender= female.text.toString()
                }

                }

                if(bangla.isChecked){
                    userSkill+="Bangla "
                }
                if(english.isChecked){
                    userSkill+="English "
                }
                if(arabic.isChecked){
                    userSkill+="Arabic "
                }
                if(hindi.isChecked){
                    userSkill+="Hindi "
                }

                val  student= Student(uri,userName,userEmail,userGender,userDate,userTime,
                userBlood,userSkill)
                val intent= Intent(this,ProfileActivity::class.java)
                intent.putExtra("student",student)
                startActivity(intent)
            }
        }
    }

    private fun takeTime() {
        val calender= Calendar.getInstance()
        val hour= calender.get(Calendar.HOUR_OF_DAY)
        val minute= calender.get(Calendar.MINUTE)

        val timePickerDialog:TimePickerDialog=TimePickerDialog(this,
            TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->

                userTime=""+hourOfDay+" : "+minute
                pickTimeText.text= ""+hourOfDay+" : "+minute

            },hour,minute,true)

        timePickerDialog.show()


    }

    private fun takeDate() {
        val calender= Calendar.getInstance()
        val year= calender.get(Calendar.YEAR)
        val month= calender.get(Calendar.MONTH)
        val day= calender.get(Calendar.DAY_OF_MONTH)
        val datePickerDialog: DatePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

            userDate= ""+dayOfMonth+"/"+monthOfYear+""+year
            pickDateText.text= ""+dayOfMonth+"/"+monthOfYear+""+year
        }, year, month+1, day)

        datePickerDialog.show()



    }

    private fun openDialogue() {
       val dialogue= AlertDialog.Builder(this)
        val option= arrayOf("Gallery","Camera")
        dialogue.setTitle("Choose a option")
        dialogue.setItems(option){ _,which ->
            val selected= option[which]

            if(selected=="Gallery"){
                openGallery()
               // Toast.makeText(this,selected,Toast.LENGTH_LONG).show()
            }
            else{
                openCamera()
                //Toast.makeText(this,selected,Toast.LENGTH_LONG).show()
            }

        }

        val alert= dialogue.create().show()

    }

    private fun openCamera() {

        val intent= Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent,2)
    }

    // App -->>> Gallery
    //

    private fun openGallery() {
        val  intent= Intent(Intent.ACTION_PICK)
        intent.type= "image/*"
        startActivityForResult(intent,1)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode== 1 && resultCode== RESULT_OK){

            uri= data?.data
            pickImage.setImageURI(data?.data)

        }
        else if(requestCode== 2 && resultCode== RESULT_OK){
            val bitmap= data?.extras?.get("data") as Bitmap
            uri= getImageUriFromBitmap(this,bitmap)
            pickImage.setImageBitmap(bitmap)


        }

    }

    fun getImageUriFromBitmap(context: Context, bitmap: Bitmap): Uri{
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, "Title", null)
        return Uri.parse(path.toString())
    }

}