package com.example.pruebalocation

import android.location.Location
import android.location.LocationListener

class Localizacion: LocationListener{
     var lat: Double
         get() {
             return lat
         }
         set(value) {}
        var lon: Double
            get() {
                return lon
            }
            set(value) {}

    override fun onLocationChanged(location: Location) {
        lat = location.latitude
        lon = location.longitude
    }
}