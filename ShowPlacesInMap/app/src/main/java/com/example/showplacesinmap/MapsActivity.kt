package com.example.showplacesinmap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Instantiate the RequestQueue
        val queue = Volley.newRequestQueue(this)
        // URL to JSON data
        val url = "https://student.labranet.jamk.fi/~M0394/countries.json"
        // A request for retrieving a JSONObject response body at a given URL
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                val countries = response.getJSONArray("countries")
                var austria = LatLng(0.0,0.0)
                for (i in 0..(countries.length()-1)) {
                    val country = countries.getJSONObject(i)
                    // Add a marker in Sydney and move the camera
                    val marker = LatLng(country.getString("lat").toDouble(), country.getString("long").toDouble())
                    val countryTitle = country.getString("country")
                    mMap.addMarker(MarkerOptions().position(marker).title(countryTitle))
                    if (countryTitle == "at") austria = marker
                }
                mMap.uiSettings.isZoomControlsEnabled = true
                // mMap.uiSettings.isCompassEnabled = true
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(austria, 4.0F))
            },
            Response.ErrorListener { error ->
                Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show()
            }

        )

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)
    }
}
