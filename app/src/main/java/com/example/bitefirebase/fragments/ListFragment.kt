package com.example.bitefirebase.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bitefirebase.R
import com.example.bitefirebase.databinding.FragmentListBinding
import com.google.firebase.auth.FirebaseAuth


class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding
    private val firebaseAuth= FirebaseAuth.getInstance()
    private val currentUser= firebaseAuth.currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentListBinding.inflate(layoutInflater)

        binding.imgLogout.setOnClickListener {
            firebaseAuth.signOut()
            replaceFragment(AuthFragment(),false)
        }
        binding.btnAdd.setOnClickListener {
            replaceFragment(InsertFragment(),true)
        }


        return binding.root
    }

    private fun replaceFragment(fragment:Fragment,isBack:Boolean) {
        if(isBack){
            activity?.supportFragmentManager?.beginTransaction()?.setCustomAnimations(
                R.anim.slide_in_right_animation,
                R.anim.slide_out_left_animation,
                R.anim.slide_in_left_animation,
                R.anim.slide_out_right_animation
            )?.replace(R.id.fragment_container,fragment)?.commit()

        }
        else{
            activity?.supportFragmentManager?.beginTransaction()?.setCustomAnimations(
                R.anim.slide_in_right_animation,
                R.anim.slide_out_left_animation,
                R.anim.slide_in_left_animation,
                R.anim.slide_out_right_animation
            )?.disallowAddToBackStack()?.replace(R.id.fragment_container,fragment)?.commit()
        }

    }
}