package com.example.bitegooglemap.fragments

import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.GeneratedAdapter
import androidx.lifecycle.lifecycleScope
import com.example.bitegooglemap.R
import com.example.bitegooglemap.adapter.PlaceAdapter
import com.example.bitegooglemap.api.RetrofitInstance
import com.example.bitegooglemap.databinding.FragmentChooseBinding
import com.example.bitegooglemap.model.PlaceModel
import com.example.bitegooglemap.utils.PermissionUtils
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import java.util.*


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class ChooseFragment : Fragment(),OnMapReadyCallback,GoogleMap.OnCameraMoveStartedListener,
GoogleMap.OnCameraMoveListener,GoogleMap.OnCameraIdleListener,PlaceAdapter.OnPlaceClickListener{

    private lateinit var binding: FragmentChooseBinding
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location
    private var city=""
    private var currentLat=0.0
    private var currentLng=0.0
    private lateinit var mMap: GoogleMap
    private lateinit var adapter: PlaceAdapter

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
                goToMapFragment()
        }
        binding.edtChooseDestination.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
               if(s.isNullOrEmpty()){
                   binding.placeRecycle.visibility= View.GONE
               }
                else{
                   binding.placeRecycle.visibility= View.VISIBLE
                   placeListApiCall(makeListQuery(s,"Your api key(need paid api key in this case"))
               }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        setAdapter()
        return binding.root
    }

    private fun setAdapter() {
        binding.placeRecycle.setHasFixedSize(true)
        adapter= PlaceAdapter(this)
        binding.placeRecycle.adapter= adapter
    }

    private fun placeListApiCall(listQuery: String) {
        lifecycleScope.launchWhenCreated {
            val response= try {
                RetrofitInstance.api.getPlaceList(listQuery)
            }catch (e:IOException){
                Log.d("api_error", "$e")
                return@launchWhenCreated
            }catch (e: HttpException){
                Log.d("api_error", "$e")
                return@launchWhenCreated
            }

            if(response.isSuccessful && response.body() != null){
                val body= response.body()!!.string()
                if(body.isNotBlank()){
                    val jsonObject= JSONObject(body)
                    val modelPlace= Gson().fromJson(jsonObject.toString(), PlaceModel::class.java)
                    val placeList= modelPlace.predictions
                    adapter.clear(true)
                    adapter.addAll(placeList!!,true)
                }
            }

        }
    }

    private fun makeListQuery(s: CharSequence?,key:String):String{
        return "maps/api/place/autocomplete/json?input=${s}&key=${key}"
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
                    binding.txtDestinationLocation.text= city
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
        binding.txtDestinationLocation.text= city
        mMap.setOnCameraMoveListener(this)

    }

    override fun onPlaceClick(placeID: String?, placeName: String?) {
        binding.txtDestinationLocation.text= placeName
        binding.edtChooseDestination.setText(placeName)
        binding.placeRecycle.visibility= View.GONE
    }
}