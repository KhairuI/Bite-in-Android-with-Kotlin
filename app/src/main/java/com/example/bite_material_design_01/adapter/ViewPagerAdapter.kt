package com.example.bite_material_design_01.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerAdapter(fm:FragmentManager,i:Int):FragmentPagerAdapter(fm,i) {

    private val fragmentList= mutableListOf<Fragment>()
    private val fragmentTitleList= mutableListOf<String>()


    override fun getCount(): Int {
      return fragmentList.size
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getPageTitle(position: Int): CharSequence {
        return fragmentTitleList[position]
    }

    fun addFragment(fragment: Fragment,title:String){
        fragmentList.add(fragment)
        fragmentTitleList.add(title)
    }

}