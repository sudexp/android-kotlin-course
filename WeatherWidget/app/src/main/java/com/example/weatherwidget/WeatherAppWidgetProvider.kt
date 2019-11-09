package com.example.weatherwidget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.util.Log
import android.widget.RemoteViews
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.AppWidgetTarget
import java.lang.Exception
import android.app.PendingIntent
import android.content.Intent

class WeatherAppWidgetProvider : AppWidgetProvider() {
    val API_link = "https://api.openweathermap.org/data/2.5/weather?q="
    val API_icon = "https://openweathermap.org/img/w/"
    val API_key = "2b240b5f7a561d96d20088eb71af8fb0"

    private val ACTION_UPDATE_CLICK = "com.example.WeatherWidget.action.UPDATE_CLICK"

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        Log.d("TAG", "updated")

        appWidgetIds.forEach { appWidgetId ->

            // get the layout
            val views = RemoteViews(context.packageName, R.layout.weather_appwidget)
            // views.setTextViewText(R.id.cityTextView, "Jyväskylä")

            // render Date and Time
            val date = Calendar.getInstance().time
            val dateFormatter = SimpleDateFormat.getDateInstance()
            val timeFormatter = SimpleDateFormat.getTimeInstance()
            val formatedDate = dateFormatter.format(date)
            val formatedTime = timeFormatter.format(date)

            views.setTextViewText(R.id.dateTextView, formatedDate)
            views.setTextViewText(R.id.timeTextView, formatedTime)

            // load data
            loadData("Jyväskylä", context, views, appWidgetId, appWidgetManager)

            // update view
            views.setOnClickPendingIntent(R.id.button,
                getPendingSelfIntent(context,
                    ACTION_UPDATE_CLICK, appWidgetId)
            )
            // appWidgetManager.updateAppWidget(appWidgetId, views)
        }

    }

    private fun getPendingSelfIntent(context: Context, action: String, appWidgetId: Int): PendingIntent {
        // An explicit intent directed at the current class (the "self").
        val intent = Intent(context, WeatherAppWidgetProvider::class.java)
        intent.action = action
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)

        // Log.d("TAG", "appWigget: " + appWidgetId.toString())

        /* val appWidgetId = intent?.extras?.getInt(
            AppWidgetManager.EXTRA_APPWIDGET_ID,
            AppWidgetManager.INVALID_APPWIDGET_ID
        ) ?: AppWidgetManager.INVALID_APPWIDGET_ID

        Log.d("TAG", "appWigget: " + appWidgetId.toString()) */

        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        // Log.d("TAG", "onreceived")

        if (ACTION_UPDATE_CLICK == intent.action) {
            Log.d("TAG", "onclicked")

            /* val views = RemoteViews(
                context.packageName,
                R.layout.weather_appwidget
            ) */

            val appWidgetManager = AppWidgetManager
                .getInstance(context)

            val appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID)

            // Log.d("TAG", appWidgetId.toString())
            // Log.d("TAG", intent.extras.toString())

            // appWidgetManager.updateAppWidget(appWidgetId, views)

            RemoteViews(context.packageName, R.layout.weather_appwidget).also { views->
                appWidgetManager.updateAppWidget(appWidgetId, views)
            }

            onUpdate(
                context,
                appWidgetManager,
                intArrayOf(appWidgetId)
            )
        }
    }

    private fun loadData (
       city: String,
       context: Context,
       views: RemoteViews,
       appWidgetId: Int,
       appWidgetManager: AppWidgetManager
    ) {
        val url = "$API_link$city&APPID=$API_key&units=metric"
        Log.d("PTM", url)

        // use volley to load weather data

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                try{
                    val mainJSONObject = response.getJSONObject("main")
                    val weatherArray = response.getJSONArray("weather")
                    val firstWeatherObject = weatherArray.getJSONObject(0)

                    // city
                    val weatherCity = response.getString("name")

                    // condition
                    val weatherCond = firstWeatherObject.getString("main")

                    // temperature
                    val weatherTemp = mainJSONObject.getString("temp")+" C"

                    // use remote views to show data
                    views.setTextViewText(R.id.cityTextView, weatherCity)
                    views.setTextViewText(R.id.condTextView, weatherCond)
                    views.setTextViewText(R.id.tempTextView, weatherTemp)

                    // icon
                    val weatherIcon = firstWeatherObject.getString("icon")
                    val iconUrl = "$API_icon$weatherIcon.png"

                    val awt: AppWidgetTarget = object : AppWidgetTarget(context.applicationContext, R.id.iconImageView, views, appWidgetId) {}

                    Glide.with(context).asBitmap().load(iconUrl).into(awt)

                    // update widget
                    appWidgetManager.updateAppWidget(appWidgetId, views)
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.d("PTM", "Some JSON exception...")
                }
            },

            Response.ErrorListener { error ->
                Log.d("PTM", "UUH - some error is happening...")
                Log.d("PTM", error.message)
            }
        )

        // start loading data with volley
        val queue = Volley.newRequestQueue(context)
        queue.add(jsonObjectRequest)

    } // loadData

} // class