package com.example.sumcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun sumNumbers(view: View) {
        // get values
        val number1 = number1.text.toString().toInt()
        val number2 = number2.text.toString().toInt()

        // calculate sum
        val sum = number1 + number2

        // display the result
        result_field.text = sum.toString()
    }
}
