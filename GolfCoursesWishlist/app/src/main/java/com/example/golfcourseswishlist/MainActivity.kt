package com.example.golfcourseswishlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var isListView = true
    private var mStaggeredLayoutManager: StaggeredGridLayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Use StaggeredGridLayoutManager as a layout manager for recyclerView
        mStaggeredLayoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = mStaggeredLayoutManager

        // Use GolfCourseWishlistAdapter as a adapter for recyclerView
        recyclerView.adapter = GolfCourseWishlistAdapter(Places.placeList())

        // ads
        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713")
        adView.loadAd(AdRequest.Builder().build())
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_toggle -> {
                if (isListView) {
                    item.setIcon(R.drawable.ic_view_module_black_24dp)
                    item.setTitle("Show as list")
                    isListView = false
                    mStaggeredLayoutManager?.setSpanCount(2)
                } else {
                    item.setIcon(R.drawable.ic_view_stream_black_24dp)
                    item.setTitle("Show as grid")
                    isListView = true
                    mStaggeredLayoutManager?.setSpanCount(1)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
