package com.example.cfprofile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class OneMore : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_more)
    }
    fun submit(view: View) {
        val handle = intent.getStringExtra("handle").toString()
        intent = Intent(applicationContext , submission::class.java)
        intent.putExtra("handle", handle)
        startActivity(intent)
    }

    fun submit1(view: View) {
        val handle = intent.getStringExtra("handle").toString()
        intent = Intent(applicationContext , difficulty::class.java)
        intent.putExtra("handle", handle)
        startActivity(intent)
    }
    fun submit2(view: View) {
        val handle = intent.getStringExtra("handle").toString()
        intent = Intent(applicationContext , latestSolved::class.java)
        intent.putExtra("handle", handle)
        startActivity(intent)
    }
}