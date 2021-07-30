package com.example.bite_material_design_01

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.bite_material_design_01.databinding.ActivityMainBinding
import com.example.bite_material_design_01.databinding.CustomViewBinding
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var openAnim:Animation
    private lateinit var closeAnim:Animation
    private var isVisible=0
    private val option= arrayOf("Male","Female","Other")
    private val fruits= arrayOf("Mango","Apple","Orange","Banana")
    private var select= booleanArrayOf(false,true,false,false)
    private var result=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        openAnim= AnimationUtils.loadAnimation(this,R.anim.fab_open)
        closeAnim= AnimationUtils.loadAnimation(this,R.anim.fab_close)

        binding.snackBar.setOnClickListener {
            val  snackbar= Snackbar.make(binding.coordinateLayout,"This is Snackbar",Snackbar.LENGTH_SHORT)
            snackbar.setAction("Undo"){
               // Snackbar.make(it,"Click undo",Snackbar.LENGTH_SHORT).show()
            }
            snackbar.duration= 5000
            snackbar.animationMode= BaseTransientBottomBar.ANIMATION_MODE_SLIDE
            //snackbar.anchorView= binding.addButton
            snackbar.show()
        }
        binding.tabActivity.setOnClickListener {
            startActivity(Intent(this,TabActivty::class.java))
        }
        binding.drawer.setOnClickListener {
            startActivity(Intent(this,DrawerActivity::class.java))
        }
        binding.customTab.setOnClickListener {
            val intent= Intent(this,CustomTab::class.java)
            intent.putExtra("page",1)
            startActivity(intent)
        }
        binding.addButton.setOnClickListener {
            if(isVisible==0){

                binding.addButton.rotation= 45F
               // binding.callBtn.visibility= View.VISIBLE
               // binding.messageBtn.visibility= View.VISIBLE
                binding.callBtn.startAnimation(openAnim)
                binding.messageBtn.startAnimation(openAnim)
                isVisible=1
            }
            else{
                binding.addButton.rotation= 0F
               // binding.callBtn.visibility= View.INVISIBLE
                //binding.messageBtn.visibility= View.INVISIBLE
                binding.callBtn.startAnimation(closeAnim)
                binding.messageBtn.startAnimation(closeAnim)
                isVisible=0
            }
        }
        binding.dialogue.setOnClickListener {
            openDialogue()
        }
        binding.callBtn.setOnClickListener {
            startActivity(Intent(this,SecondActivity::class.java))
        }
        binding.customDialogue.setOnClickListener {
            customDialogue()
        }
        binding.dialogue2.setOnClickListener {
            //customDialogue2()
            customDialogue3()

        }
        binding.datePick.setOnClickListener {
            datePick()
        }
        binding.timePick.setOnClickListener {
            timePick()
        }


    }

    private fun datePick() {
        // initilize calender
        val calendar= Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        calendar.clear()

        // get today's
        val today= MaterialDatePicker.todayInUtcMilliseconds()
        calendar.timeInMillis= today

        // start month
        calendar.set(Calendar.MONTH,Calendar.JUNE)
        val june= calendar.timeInMillis

        // end month
        calendar.set(Calendar.MONTH,Calendar.JULY)
        val july= calendar.timeInMillis

        // set constrain
        val consBuilder= CalendarConstraints.Builder()
        //consBuilder.setValidator(DateValidatorPointForward.now())
        //consBuilder.setValidator(DateValidatorPointBackward.now())
        //consBuilder.setValidator(DateValidatorPointForward.from(june))
        consBuilder.setValidator(WeekDays())

       // consBuilder.setStart(june)
        //consBuilder.setEnd(july)

        // initilize date picker
        val builder= MaterialDatePicker.Builder.datePicker()
       // val builder= MaterialDatePicker.Builder.dateRangePicker()
        builder.setTitleText("Select a Date")
      // builder.setSelection(today)
        builder.setCalendarConstraints(consBuilder.build())
        val datePicker= builder.build()
        datePicker.show(supportFragmentManager,"date_picker")
        datePicker.addOnPositiveButtonClickListener{
            val value= datePicker.headerText
            showSnackbar(convertDate(value))

        }
    }

    private fun convertDate(date:String):String{
        val df= SimpleDateFormat("MMM dd, yyyy", Locale.US)
        val myFormate= SimpleDateFormat("dd.MM.yyyy", Locale.US)
        val parse= df.parse(date)
        return myFormate.format(parse)
    }

    private fun timePick() {

        val builder= MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_12H).setTitleText("Pick your time")
        val timePicker= builder.build()
        timePicker.show(supportFragmentManager,"time_picker")
        timePicker.addOnPositiveButtonClickListener{
            val hour= timePicker.hour
            val minute= timePicker.minute
           // showSnackbar("$hour : $minute")
            setTime(hour,minute)

        }
    }

    private fun setTime(newHour: Int, newMinute: Int) {
        var time=""
        if(newHour in 0..11){
            time = "$newHour : $newMinute AM"
        } else {
            if(newHour == 12){
                time =  "$newHour : $newMinute PM"
            } else{
                val a= newHour-12
                time =  "$a : $newMinute PM"
            }
        }
        showSnackbar(time)

    }

    private fun showSnackbar(message:String){
        Snackbar.make(binding.mainLayout,message,Snackbar.LENGTH_LONG).show()
    }



    private fun customDialogue3() {
        val builder= MaterialAlertDialogBuilder(this)
        builder.setTitle("Select your fruits")
        builder.background= resources.getDrawable(R.drawable.dialogue_bg,null)
        builder.setMultiChoiceItems(fruits,select){ dialogInterface: DialogInterface, which: Int, isChecked: Boolean ->
            select[which]= isChecked
        }
        builder.setPositiveButton("YES"){ dialogInterface: DialogInterface, i: Int ->

            for(i in select.indices){
                val checked= select[i]
                if(checked){
                    result+= "${fruits[i]} "
                }
            }
            Snackbar.make(binding.mainLayout,"You have select $result",Snackbar.LENGTH_SHORT).show()
            result=""
            select= booleanArrayOf(false,true,false,false)


        }
        builder.setNegativeButton("NO"){ dialogInterface: DialogInterface, i: Int ->

        }
        builder.show()

    }

    private fun customDialogue2() {
        val builder= MaterialAlertDialogBuilder(this)
        builder.setTitle("Select your gender")
        builder.background= resources.getDrawable(R.drawable.dialogue_bg,null)
        builder.setSingleChoiceItems(option,1){ dialogInterface: DialogInterface, i: Int ->

            val value= option[i]
            Snackbar.make(binding.mainLayout,"You have select $value",Snackbar.LENGTH_SHORT).show()
            dialogInterface.dismiss()
        }
        builder.show()

    }

    private fun customDialogue() {
        val builder= MaterialAlertDialogBuilder(this)

        builder.setView(R.layout.custom_view)
        builder.background= resources.getDrawable(R.drawable.dialogue_bg,null)
        builder.show()

    }

    private fun openDialogue() {
        val builder= MaterialAlertDialogBuilder(this)
        builder.setTitle("This is Title")
        builder.setMessage("This is message")
        builder.setIcon(R.drawable.ic_call)
        builder.background= resources.getDrawable(R.drawable.dialogue_bg,null)
        builder.setPositiveButton("YES"){ dialogInterface: DialogInterface, i: Int ->
            Snackbar.make(binding.mainLayout,"Click undo",Snackbar.LENGTH_SHORT).show()

        }
        builder.setNegativeButton("NO"){ dialogInterface: DialogInterface, i: Int ->

        }
        builder.show()
    }
}