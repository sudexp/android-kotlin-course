package com.example.employeesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.employee_item.*
import org.json.JSONObject

class EmployeeActivity : AppCompatActivity() {

    // EmployeeActivity's onCreate
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee)

        // get data from intent
        val bundle: Bundle? = intent.extras;
        if (bundle != null) {
            val employeeString = bundle!!.getString("employee")
            val employee = JSONObject(employeeString)
            val name = employee["firstName"]
            // Toast.makeText(this, "Name is $name",Toast.LENGTH_LONG).show()

            nameTextView.text = employee["firstName"].toString()
            titleTextView.text = employee["title"].toString()
            emailTextView.text = employee["email"].toString()
            phoneTextView.text = employee["phone"].toString()
            departmentTextView.text = employee["department"].toString()

            val requestOptions = RequestOptions()
            requestOptions.override(500,500)
            requestOptions.centerCrop()
            requestOptions.transform(RoundedCorners(250))
            Glide.with(imageView.context).load(employee["image"].toString()).apply(requestOptions).into(imageView)
        }
    }
}
