package com.example.bitegooglemap.fragments

import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bitegooglemap.R
import com.example.bitegooglemap.databinding.FragmentDestinationBinding
import com.example.bitegooglemap.utils.PermissionUtils
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import java.util.*


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class DestinationFragment : Fragment(),OnMapReadyCallback {
    private lateinit var binding: FragmentDestinationBinding
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location
    private var city=""
    private var currentLat=0.0
    private var currentLng=0.0
    private lateinit var mMap: GoogleMap
    private lateinit var marker:Marker
    private lateinit var autoFragment: AutocompleteSupportFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentDestinationBinding.inflate(layoutInflater)
        init()
        binding.btnConfirmDestination.setOnClickListener {
            if(!(city=="")){
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

    private fun init() {
        Places.initialize(requireContext(),"Your api key(need paid api key in this case")
        autoFragment= childFragmentManager.findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment
        autoFragment.setPlaceFields(mutableListOf(
            Place.Field.ID,
            Place.Field.ADDRESS,
            Place.Field.LAT_LNG,
            Place.Field.NAME))
        autoFragment.setOnPlaceSelectedListener(object :PlaceSelectionListener{
            override fun onPlaceSelected(place: Place) {
                val currentLatLang= place.latLng
                currentLat= place.latLng?.latitude!!
                currentLng= place.latLng?.longitude!!
                city= place.address.toString()
                marker.remove()
                addMarker(currentLatLang!!)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLang,15f))
            }

            override fun onError(p0: Status) {
                Log.d("mymsg", "onError: ${p0.statusMessage}")
            }

        })


        val mapFragment= childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(p0: GoogleMap) {
        mMap= p0
        mMap.uiSettings.isMyLocationButtonEnabled= false
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
                    city= getAddress(lastLocation)[0].getAddressLine(0).toString()
                    setCountyRestrict(location)
                    addMarker(currentLatLang)
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLang,15f))
                }
            }
        }
    }

    private fun setCountyRestrict(location: Location) {
        val address= getAddress(location)
        autoFragment.setCountry(address[0].countryCode)
    }

    private fun getAddress(location: Location): MutableList<Address> {
        val geocoder = Geocoder(context, Locale.getDefault())
        return geocoder.getFromLocation(location.latitude, location.longitude, 5)
    }

    private fun addMarker(currentLatLang: LatLng) {
        marker= mMap.addMarker(MarkerOptions().position(currentLatLang))
    }


}