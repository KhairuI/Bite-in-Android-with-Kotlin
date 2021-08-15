package com.example.bitegooglemap.fragments

import android.annotation.SuppressLint
import android.icu.lang.UCharacter.getDirection
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.bitegooglemap.R
import com.example.bitegooglemap.api.RetrofitInstance
import com.example.bitegooglemap.databinding.FragmentRouteBinding
import com.example.bitegooglemap.model.Leg
import com.example.bitegooglemap.model.RouteModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.maps.android.PolyUtil
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import java.util.*


class RouteFragment : Fragment(),OnMapReadyCallback,GoogleMap.OnMarkerClickListener {

    private lateinit var binding: FragmentRouteBinding
    private lateinit var mMap:GoogleMap
    private lateinit var origin: LatLng
    private lateinit var destination: LatLng
    private  var latLngs = mutableListOf<LatLng>()
    private var paths= mutableListOf<LatLng>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentRouteBinding.inflate(layoutInflater)
        getValue()
        val mapFragment= childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
        return binding.root
    }

    private fun getValue() {
        origin= arguments?.getParcelable<LatLng>("origin")!!
        destination= arguments?.getParcelable<LatLng>("destination")!!
    }

    override fun onMapReady(p0: GoogleMap) {
      mMap= p0
        mMap.uiSettings.isZoomControlsEnabled= false
        mMap.uiSettings.isMyLocationButtonEnabled= false
        mMap.setOnMarkerClickListener(this)
        setMap()
    }

    private fun setMap() {
        val originStr= "${origin.latitude},${origin.longitude}"
        val destinationStr= "${destination.latitude},${destination.longitude}"

        Log.d("mymsg", "origin: $originStr")
        Log.d("mymsg", "destination: $destinationStr")

        val originAdd= getCityName(origin)
        mMap.addMarker(MarkerOptions().position(origin).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_user_location))
            .snippet(originAdd).title("Pick up"))

        val destinationAdd= getCityName(destination)
        mMap.addMarker(MarkerOptions().position(destination).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_red))
            .snippet(destinationAdd).title("Destination"))
        routeApiCall(makeRouteQuery(originStr,destinationStr,"your_api_key(need paid api in this case)"))

    }

    private fun routeApiCall(url: String) {
        lifecycleScope.launchWhenCreated {
            val response= try {
                RetrofitInstance.api.getRoute(url)
            }catch (e: IOException){
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
                    val modelRoute= Gson().fromJson(jsonObject.toString(), RouteModel::class.java)
                    getDirections(modelRoute)
                }
            }

        }
    }

    private fun getDirections(modelRoute: RouteModel?) {

        if(modelRoute!= null){
            val routeList= modelRoute.routes
            if(routeList.isNullOrEmpty()){
                Snackbar.make(requireView(),"No route found", Snackbar.LENGTH_SHORT).show()
            }
            else{

                val routeItem= routeList[0]
                val legItem= routeItem.legs?.get(0)
                val stepList= legItem?.steps
                setData(legItem)
                latLngs.clear()
                val iterator= stepList?.listIterator()
                for(i in iterator!!){
                    val point= i.polyline?.points
                    latLngs.addAll(PolyUtil.decode(point))
                }
                paths= latLngs
                drawPoly(paths)

            }
        }
    }

    private fun drawPoly(paths: MutableList<LatLng>) {
        val polyLine= mMap.addPolyline(PolylineOptions().addAll(paths))
        polyLine.apply {
            endCap= RoundCap()
            width= 12f
            color= ContextCompat.getColor(requireContext(),R.color.purple_500)
            jointType= JointType.ROUND
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(origin,15f))
    }

    @SuppressLint("SetTextI18n")
    private fun setData(legItem: Leg?) {

        val distance= (legItem?.distance?.value)?.div(1000.00)
        binding.txtDistance.text= "${String.format("%.2f",distance)} KM"
        val time= ((legItem?.duration?.value)?.div(60))
        binding.txtTime.text= "$time Min"
    }

    private fun makeRouteQuery(origin:String,des:String,key:String):String{
        return "maps/api/directions/json?origin=${origin}&destination=${des}&mode=DRIVING&key=${key}&language=en"
    }

    override fun onMarkerClick(p0: Marker)= false

    private fun getCityName(latLng: LatLng): String {
        val geocoder = Geocoder(context, Locale.getDefault())
        val address= geocoder.getFromLocation(latLng.latitude, latLng.longitude, 5)
        return address?.get(0)?.getAddressLine(0).toString()
    }

}