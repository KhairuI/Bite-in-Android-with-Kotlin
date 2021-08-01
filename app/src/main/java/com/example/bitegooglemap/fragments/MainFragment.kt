package com.example.bitegooglemap.fragments

import android.content.DialogInterface
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import com.example.bitegooglemap.R
import com.example.bitegooglemap.databinding.FragmentMainBinding
import com.example.bitegooglemap.utils.PermissionUtils
import com.google.android.gms.location.*
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.*

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentMainBinding.inflate(layoutInflater)

        binding.btnMap.setOnClickListener {
            if(PermissionUtils.checkPermission(requireContext())){
                Log.d("mymsg", "on Permission: Granted")
                if(PermissionUtils.isGPSEnable(requireContext())){
                    Log.d("mymsg", "on GPS: GPS Enable")
                    goToMapActivity()
                }
                else{
                    PermissionUtils.enableGPS(requireContext())
                }

            }
            else{
                requestMultiplePermission.launch(
                    arrayOf(PermissionUtils.ACCESS_FINE_LOCATION,PermissionUtils.ACCESS_COARSE_LOCATION)
                )
            }

        }
        binding.btnCurrentLocation.setOnClickListener {
            if(PermissionUtils.checkPermission(requireContext())){
                if(PermissionUtils.isGPSEnable(requireContext())){
                    Log.d("mymsg", "on GPS: GPS Enable")
                    fusedLocationProviderClient.lastLocation.addOnCompleteListener {
                        val location= it.result
                        if(location== null){
                            newLocation()
                        }
                        else{
                            showDialogue(location)
                        }
                    }

                }
                else{
                    PermissionUtils.enableGPS(requireContext())
                }

            }
            else{
                requestMultiplePermission.launch(
                    arrayOf(PermissionUtils.ACCESS_FINE_LOCATION,PermissionUtils.ACCESS_COARSE_LOCATION)
                )
            }

        }
        return binding.root
    }

    private fun newLocation() {
        locationRequest= LocationRequest.create().apply {
            interval = 0
            fastestInterval = 0
            priority= LocationRequest.PRIORITY_HIGH_ACCURACY
            numUpdates= 2
        }
        if(PermissionUtils.checkPermission(requireContext())){
            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest,locationCallBack,Looper.myLooper()
            )
        }
        else{
            requestMultiplePermission.launch(
                arrayOf(PermissionUtils.ACCESS_FINE_LOCATION,PermissionUtils.ACCESS_COARSE_LOCATION)
            )
        }
    }

    private val locationCallBack= object :LocationCallback(){
        override fun onLocationResult(p0: LocationResult) {
            val lastLocation= p0.lastLocation
            showDialogue(lastLocation)
        }
    }

    private fun showDialogue(location: Location) {
        val address= getAddress(location)
        val builder= MaterialAlertDialogBuilder(requireContext())
        builder.setTitle("Current Location")
        builder.setIcon(R.drawable.ic_location)
        builder.setMessage("Latitude: ${location.latitude} \nLongitude: ${location.longitude}" +
                " \nAddress: ${address[0].getAddressLine(0)} \nCountry: ${address[0].countryName}")
        builder.background = resources.getDrawable(R.drawable.dialogue_bg,null)
        builder.setNegativeButton("Close"){ dialogInterface: DialogInterface, i: Int ->

        }
        builder.show()

    }

    private fun getAddress(location: Location):MutableList<Address>{
        val geocoder= Geocoder(context, Locale.getDefault())
        val address= geocoder.getFromLocation(location.latitude,location.longitude,5)
        return address
    }


    private val requestMultiplePermission= registerForActivityResult(ActivityResultContracts.
   RequestMultiplePermissions()){ permissions->
       if(permissions[PermissionUtils.ACCESS_FINE_LOCATION] == true &&
           permissions[PermissionUtils.ACCESS_COARSE_LOCATION] == true){
           Log.d("mymsg", "on Permission: Granted")
       }
       else{
           Log.d("mymsg", "on Permission: Permission Not Granted")
       }
   }

    private fun goToMapActivity() {
        activity?.supportFragmentManager?.beginTransaction()?.setCustomAnimations(
            R.anim.slide_in_right_animation,
            R.anim.slide_out_left_animation,
            R.anim.slide_in_left_animation,
            R.anim.slide_out_right_animation
        )?.replace(R.id.fragment_container,MapFragment())?.addToBackStack(null)?.commit()
    }

    companion object {

    }
}