package com.example.bitegooglemap.fragments

import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bitegooglemap.R
import com.example.bitegooglemap.databinding.FragmentChooseBinding
import com.example.bitegooglemap.utils.PermissionUtils
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import java.util.*


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class ChooseFragment : Fragment(),OnMapReadyCallback,GoogleMap.OnCameraMoveStartedListener,
GoogleMap.OnCameraMoveListener,GoogleMap.OnCameraIdleListener{

    private lateinit var binding: FragmentChooseBinding
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location
    private var city=""
    private var currentLat=0.0
    private var currentLng=0.0
    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentChooseBinding.inflate(layoutInflater)
        val mapFragment= childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
        binding.btnConfirmDestination.setOnClickListener {
            if(binding.edtChooseDestination.text.toString().isNotEmpty()){
                goToMapFragment()
            }
        }

        return binding.root
    }

    private fun goToMapFragment() {
        val bundle= Bundle()
        val latLng= LatLng(currentLat,currentLng)
        bundle.putParcelable("latlng",latLng)
        bundle.putString("city",city)
        val mapFragment= MapFragment()
        mapFragment.arguments= bundle
        activity?.supportFragmentManager?.beginTransaction()
            ?.setCustomAnimations(
                R.anim.slide_in_right_animation,
                R.anim.slide_out_left_animation,
                R.anim.slide_in_left_animation,
                R.anim.slide_out_right_animation
            )?.disallowAddToBackStack()?.replace(R.id.fragment_container,mapFragment)?.commit()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap= googleMap
        mMap.uiSettings.isZoomControlsEnabled= false
        mMap.uiSettings.isMyLocationButtonEnabled= false
        mMap.setOnCameraIdleListener(this)
        mMap.setOnCameraMoveStartedListener(this)
        setMap()
    }

    private fun setMap() {
        if(PermissionUtils.checkPermission(requireContext())){
            mMap.isMyLocationEnabled= true
            fusedLocationProviderClient.lastLocation.addOnCompleteListener {
                val location= it.result
                if(location != null){
                    lastLocation= location
                    currentLat= location.latitude
                    currentLng= location.longitude
                    val currentLatLang= LatLng(lastLocation.latitude,lastLocation.longitude)
                    val address= getAddress(lastLocation)
                    city= address[0].getAddressLine(0).toString()
                    binding.edtChooseDestination.setText(city)
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLang,15f))
                }
            }
        }
    }
    private fun getAddress(location: Location): MutableList<Address> {
        val geocoder = Geocoder(context, Locale.getDefault())
        return geocoder.getFromLocation(location.latitude, location.longitude, 5)
    }


    override fun onCameraMoveStarted(reason: Int) {
        if(reason== GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE){
            mMap.setOnCameraMoveStartedListener(this)
        }
    }

    override fun onCameraMove() {
    }

    override fun onCameraIdle() {
        val cameraPosition= mMap.cameraPosition
        val location= Location(LocationManager.GPS_PROVIDER)
        location.apply {
            latitude= cameraPosition.target.latitude
            longitude= cameraPosition.target.longitude
        }
        currentLat= location.latitude
        currentLng= location.longitude
        val address= getAddress(location)
        city= address[0].getAddressLine(0).toString()
        binding.edtChooseDestination.setText(city)
        mMap.setOnCameraMoveListener(this)

    }
}