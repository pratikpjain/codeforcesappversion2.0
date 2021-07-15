package com.example.cfprofile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val progressBar = findViewById<View>(R.id.progressBar) as ProgressBar
        progressBar.visibility = View.INVISIBLE

        val frds = findViewById<View>(R.id.frd) as Button

        frds.setOnClickListener(View.OnClickListener {
            intent = Intent(applicationContext , Friends::class.java)
            startActivity(intent)
        })
    }
    fun go(view: View) {
        Check()
    }
    private fun Check() {
        val progressBar = findViewById<View>(R.id.progressBar) as ProgressBar
        progressBar.visibility = View.VISIBLE;
        val myhdl = findViewById<View>(R.id.handle) as EditText
        val handle = myhdl.text.toString();
        val queue = Volley.newRequestQueue(this)
        val url = "https://codeforces.com/api/user.info?handles=$handle&key=6b2b9c56e6e5811b17a63cd66ec4bdc8b9e9401b"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                val url1 = response.getString("status");
                if(url1.toString() == "OK") {
                    call_intent()
                }
                else {
                    val progressBar = findViewById<View>(R.id.progressBar) as ProgressBar
                    progressBar.visibility = View.INVISIBLE;
                    Toast.makeText(applicationContext , "No such handle exists!!" , Toast.LENGTH_LONG).show()
                }
            },
            { error ->
                val progressBar = findViewById<View>(R.id.progressBar) as ProgressBar
                progressBar.visibility = View.INVISIBLE;
                Toast.makeText(applicationContext , "Something Went Wrong!!" , Toast.LENGTH_LONG).show()
            }
        )
        queue.add(jsonObjectRequest)
    }
    fun call_intent() {
        val myhdl = findViewById<View>(R.id.handle) as EditText
        val handle = myhdl.text.toString();
        val progressBar = findViewById<View>(R.id.progressBar) as ProgressBar
        progressBar.visibility = View.INVISIBLE;
        intent = Intent(applicationContext , DisplayInfo::class.java)
        intent.putExtra("handle", handle)
        startActivity(intent)
    }
}