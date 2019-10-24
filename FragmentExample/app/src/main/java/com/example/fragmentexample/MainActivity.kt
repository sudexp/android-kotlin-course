package com.example.fragmentexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun openFragmentOne(view: View) {
        val fragmentOne = FragmentOne()
        supportFragmentManager.beginTransaction().replace(R.id.fragment, fragmentOne).commit()
    }

    fun openFragmentTwo(view: View) {
        val fragmentTwo = FragmentTwo()
        supportFragmentManager.beginTransaction().replace(R.id.fragment, fragmentTwo).commit()
    }
}
