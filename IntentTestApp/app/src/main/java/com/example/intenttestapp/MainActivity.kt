package com.example.intenttestapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val SECOND_ACTIVITY_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun handleClick(view: View) {
        val name = editText.text.toString()
        // Toast.makeText(this, "Name is " + name, Toast.LENGTH_LONG). show()
        // Log.d("test", "Name is " + name)

        val intent = Intent(this, SecondActivity::class.java)
        intent.putExtra("name", name)
        startActivity(intent)
    }

    fun handleAnotherButtonClick(view: View) {
        val name = editText.text.toString()
        val intent = Intent(this, SecondActivity::class.java)
        intent.putExtra("name", name)
        startActivityForResult(intent, SECOND_ACTIVITY_REQUEST)
    }

    // get result from another activities
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // check which request we're responding to
        if (requestCode == SECOND_ACTIVITY_REQUEST) {
            // make sure the request was successful
            if (resultCode == Activity.RESULT_OK) {
                // the Intent's data Uri identifies data send here
                if (data != null) {
                    // safe use for nullable bundle
                    val number = data.extras?.getDouble("number")
                    Toast.makeText(this, "Activity returned random number $number", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
