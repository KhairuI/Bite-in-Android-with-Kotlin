package com.example.bite_material_design_01

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.bite_material_design_01.databinding.ActivityMainBinding
import com.example.bite_material_design_01.databinding.CustomViewBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var openAnim:Animation
    private lateinit var closeAnim:Animation
    private var isVisible=0
    private val option= arrayOf("Male","Female","Other")
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
        binding.customDialogue.setOnClickListener {
            customDialogue()
        }
        binding.dialogue2.setOnClickListener {
            customDialogue2()
        }

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