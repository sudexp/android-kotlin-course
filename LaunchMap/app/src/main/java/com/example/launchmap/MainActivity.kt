package com.example.launchmap

import android.content.Intent
import android.content.pm.ResolveInfo
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun showMap(view: View) {
        // Get latitude and longitude values
        val lat = latitude.text.toString().toDouble()
        // Toast.makeText(this, "Latitude is " + lat, Toast.LENGTH_LONG). show()

        val lng = longitude.text.toString().toDouble()
        // Toast.makeText(this, "Longitude is " + lng, Toast.LENGTH_LONG). show()

        // Build the intent
        val location = Uri.parse("geo:$lat,$lng")
        val mapIntent = Intent(Intent.ACTION_VIEW, location)

        // Verify it resolves
        val activities: List<ResolveInfo> = packageManager.queryIntentActivities(mapIntent, 0)
        val isIntentSafe: Boolean = activities.isNotEmpty()

        // Start an activity if it's safe
        if (isIntentSafe) {
            startActivity(mapIntent)
        } else {
            Toast.makeText(this, "There is no activity to handle map intent!", Toast.LENGTH_LONG).show();
        }
    }
}
