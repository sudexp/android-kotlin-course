package com.example.intenttestapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ActionMode
import android.view.View
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        // get name from the intent data
        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            val name = bundle.getString("name")
            // textView.text = "Hello " + name + "!"
            textView.text = getString(R.string.hello_text, name)
        }
    }

    fun handleBackButton (view: View) {
        finish()
    }

    fun handleAnotherButtonClick(view: View) {
        val intent = Intent()
        intent.putExtra("number", Math.random())
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}
