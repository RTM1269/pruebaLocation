package com.example.pruebalocation

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.Debug.getLocation
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var location: LocationManager
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        //localizacion()
        checkPermissions()


    }

    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            //Pido que los acepte.
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION),1)
        }else{
           getLocationOK()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode==1){
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)==PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
                    Log.i("infoRTM","procedemos a obtener las localizaciones")
                    getLocationOK()
                }else{
                    Log.i("infoRTM","No tenemos permisos para obtener las localizaciones")
                }
            }
        }
    }

    private fun getLocationOK() {
        //Obligatorio por el ID.
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationProviderClient.lastLocation?.addOnSuccessListener {
            if (it == null){

            }else it.apply {
                val latitude = it.latitude
                val longitude = it.longitude
                Log.i("infoRTM",latitude.toString())
                Log.i("infoRTM",longitude.toString())
                getCityName(longitude,latitude)
                getCityName(-3.5314,40.4228)
            }
        }
    }

    fun getCityName(longitude:Double,latitude:Double){
        var cityName = "Not found"
        val gcd = Geocoder(baseContext, Locale.getDefault())
        try {
            val address:List<Address> = gcd.getFromLocation(latitude,longitude,10)

            for (add in address){
                var city = add?.locality
                var c = add?.countryName
                if (!city.equals("")){
                    cityName = city
                    Log.i("infoRTM",cityName + c)
                }else{
                    Log.d(
                        "DEbugsss","city not FOUND"
                    )
                }
            }
        }catch (e: Exception){

        }
    }

}