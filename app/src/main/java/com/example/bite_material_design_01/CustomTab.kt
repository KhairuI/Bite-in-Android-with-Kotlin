package com.example.bite_material_design_01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.example.bite_material_design_01.adapter.ViewPagerAdapter
import com.example.bite_material_design_01.databinding.ActivityCustomTabBinding
import com.example.bite_material_design_01.fragments.CallFragment
import com.example.bite_material_design_01.fragments.MessageFragment

class CustomTab : AppCompatActivity() {
    private lateinit var binding: ActivityCustomTabBinding
    private lateinit var adapter: ViewPagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityCustomTabBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setViewPager()
        getPage()
        binding.btnCall.setOnClickListener {
            onCallSelect()
        }
        binding.btnMessage.setOnClickListener {
            onMessageSelect()
        }

    }

    private fun getPage() {
        val page= intent.getIntExtra("page",0)
        if(page==1) onCallSelect()
        else onMessageSelect()
    }

    private fun setViewPager() {
        adapter= ViewPagerAdapter(supportFragmentManager,0).apply {
            addFragment(MessageFragment(),"Message")
            addFragment(CallFragment(),"Call")
        }
        binding.viewPager.adapter= adapter
        binding.viewPager.addOnPageChangeListener(object :ViewPager.OnPageChangeListener{
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                if(position==0) onMessageSelect()
                else onCallSelect()

            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })
    }

    private fun onCallSelect() {
        // set self
        binding.btnCall.strokeColor= ContextCompat.getColorStateList(this,R.color.select)
        binding.btnCall.setTextColor(ContextCompat.getColorStateList(this,R.color.select))

        // other
        binding.btnMessage.strokeColor= ContextCompat.getColorStateList(this,R.color.not_select)
        binding.btnMessage.setTextColor(ContextCompat.getColorStateList(this,R.color.not_select))

        binding.viewPager.setCurrentItem(1,true)

    }

    private fun onMessageSelect() {
        binding.btnMessage.strokeColor= ContextCompat.getColorStateList(this,R.color.select)
        binding.btnMessage.setTextColor(ContextCompat.getColorStateList(this,R.color.select))

        binding.btnCall.strokeColor= ContextCompat.getColorStateList(this,R.color.not_select)
        binding.btnCall.setTextColor(ContextCompat.getColorStateList(this,R.color.not_select))


        binding.viewPager.setCurrentItem(0,true)
    }
}