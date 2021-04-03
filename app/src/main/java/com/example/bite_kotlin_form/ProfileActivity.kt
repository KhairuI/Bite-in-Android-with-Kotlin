package com.example.bite_kotlin_form

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val student: Student?= intent.getParcelableExtra("student")
        if (student != null){
            profileImage.setImageURI(student.uri)
            nameText.text= student.name
            emailText.text= student.email
            genderText.text= student.gender
            dateText.text= student.date
            timeText.text= student.time
            bloodText.text= student.blood
            skillText.text= student.skill
        }
    }
}