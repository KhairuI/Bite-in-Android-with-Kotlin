package com.example.bite_material_design_01

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.TextureView
import android.widget.TextView
import com.example.bite_material_design_01.databinding.ActivitySecondBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar

class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding
    private lateinit var bottomSheetDialog: BottomSheetDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.bottomBar)


        binding.addBtn.setOnClickListener {
            startActivity(Intent(this,ThirdActivity::class.java))
        }
        binding.btnBottomSheet.setOnClickListener {
            openBottomSheet()
        }
    }

    private fun openBottomSheet() {
        bottomSheetDialog= BottomSheetDialog(this)
        val view= LayoutInflater.from(this).inflate(R.layout.bottom_sheet,null,false)
        val call= view.findViewById<TextView>(R.id.tv_call)
        val message= view.findViewById<TextView>(R.id.tv_message)
        val upload= view.findViewById<TextView>(R.id.tv_upload)

        call.setOnClickListener {
            showSnackbar("Call")
            bottomSheetDialog.dismiss()
        }
        message.setOnClickListener {
            showSnackbar("Message")
            bottomSheetDialog.dismiss()
        }
        upload.setOnClickListener {
            showSnackbar("Upload")
            bottomSheetDialog.dismiss()
        }


        bottomSheetDialog.apply {
            setContentView(view)
            setCanceledOnTouchOutside(true)
            dismissWithAnimation= true
        }

        if(this:: bottomSheetDialog.isInitialized){
            bottomSheetDialog.show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.bottom_app_bar_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.messId ->{
                showSnackbar("Message")
            }
            R.id.profile ->{
                showSnackbar("Profile")
            }
            R.id.setting ->{
                showSnackbar("Setting")

            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showSnackbar(message:String){
        Snackbar.make(binding.topLayout,message, Snackbar.LENGTH_LONG).show()
    }
}