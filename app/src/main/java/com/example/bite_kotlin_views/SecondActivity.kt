package com.example.bite_kotlin_views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        imageButton.setOnClickListener(this)
        plusButton.setOnClickListener(this)
        minusButton.setOnClickListener(this)

    }

    override fun onClick(view:View?) {
       when(view?.id){
           R.id.imageButton ->{
               val value= imageButton.text.toString()

               if(value=="Google"){
                   image.setImageResource(R.drawable.sign_icon)
                   imageButton.text="Profile"
               }
               else{
                   image.setImageResource(R.drawable.profile)
                   imageButton.text="Google"
               }
           }
           R.id.plusButton ->{
               //string --- Int --- String
               val value= numberText.text.toString()
               val num= value.toInt()+1
               numberText.text= num.toString()

           }

           R.id.minusButton ->{

               val value= numberText.text.toString()
               val num= value.toInt()-1
               numberText.text= num.toString()
           }

       }
    }
}