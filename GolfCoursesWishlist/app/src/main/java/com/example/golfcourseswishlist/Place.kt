package com.example.golfcourseswishlist

import android.content.Context

class Place {
    var name: String? = null
    var image: String? = null

    fun getImageResourceId(context: Context): Int {
        return context.resources.getIdentifier(this.image,"drawable", context.packageName)
    }
}