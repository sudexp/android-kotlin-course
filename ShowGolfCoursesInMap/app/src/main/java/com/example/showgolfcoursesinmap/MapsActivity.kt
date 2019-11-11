package com.example.showgolfcoursesinmap

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

import com.google.android.gms.maps.model.BitmapDescriptor
import android.widget.TextView
import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import com.google.android.gms.maps.model.Marker

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

        setWindowInfo(mMap)

        // Instantiate the RequestQueue
        val queue = Volley.newRequestQueue(this)
        // URL to JSON data
        val url = "http://ptm.fi/materials/golfcourses/golf_courses.json"
        // A request for retrieving a JSONObject response body at a given URL
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                var finland = LatLng(62.0,26.0)
                val courses = response.getJSONArray("courses")
                for (i in 0..(courses.length()-1)) {
                    val course = courses.getJSONObject(i)
                    val marker = LatLng(course.getDouble("lat"), course.getDouble("lng"))
                    val courseTitle = course.getString("course")
                    val description = courseTitle + "\n" + course.getString("address") + "\n" + course.getString("phone") + "\n" + course.getString("email") + "\n" + course.getString("web")
                    val type = course.getString("type")
                    mMap.addMarker(MarkerOptions().position(marker).title(courseTitle).snippet(description).icon(getMarkerColor(type)))
                }
                mMap.uiSettings.isZoomControlsEnabled = true
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(finland, 5.8F))
            },
            Response.ErrorListener { error ->
                Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show()
            }

        )

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)
    }

    private fun getMarkerColor(type: String): BitmapDescriptor {
        if (type == "Kulta") {
            return BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)
        } else if (type == "Kulta/Etu") {
            return BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)
        } else if (type == "Etu") {
            return BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
        } else {
            return BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
        }
    }

    private fun setWindowInfo(mMap: GoogleMap) {
        mMap.setInfoWindowAdapter(object : GoogleMap.InfoWindowAdapter {

            override fun getInfoWindow(arg0: Marker): View? {
                return null
            }

            override fun getInfoContents(marker: Marker): View {
                val mContext = applicationContext

                val info = LinearLayout(mContext)
                info.orientation = LinearLayout.VERTICAL

                val title = TextView(mContext)
                title.setTextColor(Color.BLACK)
                title.gravity = Gravity.CENTER
                title.setTypeface(null, Typeface.BOLD)
                title.text = marker.title

                val snippet = TextView(mContext)
                snippet.setTextColor(Color.GRAY)
                snippet.text = marker.snippet

                info.addView(title)
                info.addView(snippet)

                return info
            }
        })

    }

}
