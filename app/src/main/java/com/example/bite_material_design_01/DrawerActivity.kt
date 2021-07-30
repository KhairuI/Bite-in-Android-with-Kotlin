package com.example.bite_material_design_01

import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.example.bite_material_design_01.databinding.ActivityDrawerBinding
import com.example.bite_material_design_01.fragments.CallFragment
import com.example.bite_material_design_01.fragments.InsertFragment
import com.example.bite_material_design_01.fragments.MessageFragment
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar

class DrawerActivity : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityDrawerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityDrawerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.drawerToolbar)
        title="Drawer"
        setDrawer()
        if(savedInstanceState==null){
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container,MessageFragment()).commit()
            binding.navigationView.setCheckedItem(R.id.message_dr)
        }

    }

    private fun setDrawer() {
        val toogle= ActionBarDrawerToggle(this,binding.layoutDrawer,binding.drawerToolbar
            ,R.string.close_drawer,R.string.open_drawer)
        binding.layoutDrawer.addDrawerListener(toogle)
        toogle.syncState()
        binding.navigationView.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.call_dr ->{
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container,CallFragment()).commit()
            }
            R.id.message_dr ->{
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container,MessageFragment()).commit()
            }
            R.id.upload_dr ->{
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container,InsertFragment()).commit()
            }
            R.id.setting_dr ->{
                    shawSnackBar("Setting")
            }
            R.id.share_dr ->{
                shawSnackBar("Share")
            }
        }
        binding.layoutDrawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onPointerCaptureChanged(hasCapture: Boolean) {
        super.onPointerCaptureChanged(hasCapture)
    }

    override fun onBackPressed() {
        if(binding.layoutDrawer.isDrawerOpen(GravityCompat.START)){
            binding.layoutDrawer.closeDrawer(GravityCompat.START)
        }
        else{
            super.onBackPressed()
        }

    }


    fun shawSnackBar(message:String){
        Snackbar.make(binding.layoutDrawer,message, Snackbar.LENGTH_SHORT).show()
    }

}