package com.example.bitefirebase.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bitefirebase.R
import com.google.firebase.auth.FirebaseAuth


class SplashFragment : Fragment() {

    private val firebaseAuth= FirebaseAuth.getInstance()
    private val currentUser= firebaseAuth.currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if(currentUser==null){
            replaceFragment(AuthFragment())
        }
        else{
            replaceFragment(ListFragment())
        }
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    private fun replaceFragment(fragment:Fragment) {
        activity?.supportFragmentManager?.beginTransaction()?.setCustomAnimations(
            R.anim.slide_in_right_animation,
            R.anim.slide_out_left_animation,
            R.anim.slide_in_left_animation,
            R.anim.slide_out_right_animation
        )?.disallowAddToBackStack()?.replace(R.id.fragment_container,fragment)?.commit()
    }

}