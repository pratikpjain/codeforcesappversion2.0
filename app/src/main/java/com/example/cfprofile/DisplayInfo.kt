package com.example.cfprofile

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.example.cfprofile.Data.MyDb
import com.example.cfprofile.Model.User
import java.sql.Date
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class DisplayInfo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_info)

        val addtofriend = findViewById<View>(R.id.addto) as Button

        val remove = findViewById<View>(R.id.remove) as Button

        addtofriend.setEnabled(false);

        remove.setEnabled(false);

        val handle = intent.getStringExtra("handle").toString()
        loadData(handle)

        val db: MyDb = MyDb(this)
        val user: User = User(handle + "" , "" , "");
        if(db.is_Friend(user)) {
            remove.setEnabled(true)
        }
        else {
            addtofriend.setEnabled(true)
        }

        addtofriend.setOnClickListener(View.OnClickListener {
            db.addFriend(user)
            Toast.makeText(applicationContext , "$handle is your new Friend!!" , Toast.LENGTH_LONG).show()
            addtofriend.setEnabled(false)
            remove.setEnabled(true)
        })

        remove.setOnClickListener(View.OnClickListener {
            db.removeFriend(handle)
            Toast.makeText(applicationContext , "$handle is removed from your Friends list" , Toast.LENGTH_LONG).show()
            addtofriend.setEnabled(true)
            remove.setEnabled(false)
        })

    }
//    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadData(handle : String){

        val addtofriend = findViewById<View>(R.id.addto) as Button
        val remove = findViewById<View>(R.id.remove) as Button

        val titlephoto = findViewById<View>(R.id.titlephoto) as ImageView
        val hdl = findViewById<View>(R.id.hdl) as TextView
        val rate = findViewById<View>(R.id.rate) as TextView
        val rank = findViewById<View>(R.id.rank) as TextView
        val fname = findViewById<View>(R.id.fname) as TextView
        val lname = findViewById<View>(R.id.lname) as TextView
        val org = findViewById<View>(R.id.org) as TextView
        val maxR = findViewById<View>(R.id.maxR) as TextView
        val maxRT = findViewById<View>(R.id.maxRT) as TextView
        val lseen = findViewById<View>(R.id.ls) as TextView

        val queue = Volley.newRequestQueue(this)
        val url = "https://codeforces.com/api/user.info?handles=$handle&key=6b2b9c56e6e5811b17a63cd66ec4bdc8b9e9401b"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                val respo = response.getString("status")
                if(respo != "OK") {
                    changeIntent();
                }
                var lastSeen = "0".toLong()
                val url1 = response.getJSONArray("result");
                val url2 = url1.getJSONObject(0);
                if(url2.has("titlePhoto") == true)
                    Glide.with(this).load(url2.opt("titlePhoto").toString()).into(titlephoto)
                if(url2.has("handle") == true)
                    hdl.setText(url2.opt("handle").toString())
                if(url2.has("rating") == true)
                    rate.setText(url2.opt("rating").toString())
                if(url2.has("rank") == true)
                    rank.setText(url2.opt("rank").toString())
                if(url2.has("firstName") == true)
                    fname.setText(url2.opt("firstName").toString())
                if(url2.has("lastName") == true)
                    lname.setText(url2.opt("lastName").toString())
                if(url2.has("organization") == true)
                    org.setText(url2.opt("organization").toString())
                if(url2.has("maxRank") == true)
                    maxR.setText(url2.opt("maxRank").toString())
                if(url2.has("maxRating") == true)
                    maxRT.setText(url2.opt("maxRating").toString())
                if(url2.has("lastOnlineTimeSeconds") == true)
                    lastSeen = url2.opt("lastOnlineTimeSeconds").toString().toLong()
                var curr_time = System.currentTimeMillis()
                curr_time = curr_time/1000
                val secs = curr_time - lastSeen
                val mins = secs/60
                val hrs = mins/60
                val days = hrs/24
                val weeks = days/7
                val months = weeks/4
                val yrs = months/12
                if(yrs > 0) {
                    lseen.setText("LastSeen: " + yrs.toString() + " years ago")
                }
                else if(months > 0) {
                    lseen.setText("LastSeen: " + months.toString() + " Months ago")
                }
                else if(weeks > 0) {
                    lseen.setText("LastSeen: " + weeks.toString() + " Weeks ago")
                }
                else if(days > 0) {
                    lseen.setText("LastSeen: " + days.toString() + " Days ago")
                }
                else if(hrs > 0) {
                    lseen.setText("LastSeen: " + hrs.toString() + " hrs ago")
                }
                else if(mins > 0) {
                    lseen.setText("LastSeen: " + mins.toString() + " mins ago")
                }
                else if(secs > 0) {
                    lseen.setText("LastSeen: " + secs.toString() + " sec ago")
                }
                val pb = findViewById<View>(R.id.progressBar2) as ProgressBar
                pb.visibility = View.INVISIBLE
            },
            { error ->
                val pb = findViewById<View>(R.id.progressBar2) as ProgressBar
                pb.visibility = View.INVISIBLE
                changeIntent();
            }
        )
        queue.add(jsonObjectRequest)
    }

    fun mybtn(view: View) {
        val handle = intent.getStringExtra("handle").toString()
        intent = Intent(applicationContext , OneMore::class.java)
        intent.putExtra("handle", handle)
        startActivity(intent)
    }
    fun changeIntent() {
        Toast.makeText(applicationContext , "Something Went Wrong!!" , Toast.LENGTH_LONG).show()
        intent = Intent(applicationContext , MainActivity::class.java)
        startActivity(intent)
    }
}