package com.example.bite_material_design_01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bite_material_design_01.adapter.ViewPagerAdapter
import com.example.bite_material_design_01.databinding.ActivityTabActivtyBinding
import com.example.bite_material_design_01.fragments.CallFragment
import com.example.bite_material_design_01.fragments.InsertFragment
import com.example.bite_material_design_01.fragments.MessageFragment

class TabActivty : AppCompatActivity() {


    private lateinit var binding: ActivityTabActivtyBinding
    private lateinit var adapter: ViewPagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityTabActivtyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        title="Contact Tab"

        binding.tabLayout.setupWithViewPager(binding.viewPager)
        adapter= ViewPagerAdapter(supportFragmentManager,0).apply {
            addFragment(MessageFragment(),"Message")
            addFragment(CallFragment(),"Call")
            addFragment(InsertFragment(),"Insert")
        }
        binding.viewPager.adapter= adapter

        binding.tabLayout.apply {
            getTabAt(0)?.setIcon(R.drawable.ic_message)
            getTabAt(1)?.setIcon(R.drawable.ic_call)
            getTabAt(2)?.setIcon(R.drawable.ic_add)
        }

        val messageBadge= binding.tabLayout.getTabAt(0)?.orCreateBadge
        messageBadge?.apply {
            isVisible= true
            number= 5
        }



    }
}