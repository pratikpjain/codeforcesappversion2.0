package com.example.cfprofile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

class difficulty : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_difficulty)
        val handle = intent.getStringExtra("handle").toString()
        val hdlname = findViewById<View>(R.id.hdlname2) as TextView
        hdlname.setText("Hii, " + handle);
        process1(handle);
    }
    private fun process1(handle : String) {
        val xtra = findViewById<View>(R.id.xtra2) as TextView
        val one = findViewById<View>(R.id.one) as TextView
        val two = findViewById<View>(R.id.two) as TextView
        val three = findViewById<View>(R.id.three) as TextView
        val four = findViewById<View>(R.id.four) as TextView
        val five = findViewById<View>(R.id.five) as TextView
        val six = findViewById<View>(R.id.six) as TextView
        val seven = findViewById<View>(R.id.seven) as TextView
        val tot = findViewById<View>(R.id.total2) as TextView
        val queue = Volley.newRequestQueue(this)
        val url = "https://codeforces.com/api/user.status?handle=$handle&key=6b2b9c56e6e5811b17a63cd66ec4bdc8b9e9401b"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                val respo = response.getString("status")
                if(respo != "OK") {
                    changeIntent();
                }
                val url1 = response.getJSONArray("result");
                val len = url1.length()
                var ans = 0;
                var rat = 0;
                var a = 0 ; var b = 0;var c = 0 ; var d = 0;var e = 0 ; var f = 0;var g = 0 ;
                val arr = mutableSetOf<String>()
                for(i in 0..len-1) {
                    if(url1.getJSONObject(i).opt("verdict").toString() != "OK") continue
                    var temp1 = "";
                    var temp2 = "";
                    if(url1.getJSONObject(i).getJSONObject("problem").has("index") == true)
                        temp1 = url1.getJSONObject(i).getJSONObject("problem").opt("index").toString()
                    if(url1.getJSONObject(i).getJSONObject("problem").has("contestId") == true)
                        temp2 = url1.getJSONObject(i).getJSONObject("problem").opt("contestId").toString()
                    if(url1.getJSONObject(i).getJSONObject("problem").has("rating") == true)
                        rat = url1.getJSONObject(i).getJSONObject("problem").opt("rating").toString().toInt()
                    val ele = temp2 + temp1;
                    val len1 = arr.size;
                    arr.add(ele)
                    val len2 = arr.size;
                    if(len2 != len1) {
                        if(rat in 800..1199) {
                            a += 1
                        }
                        else if(rat in 1200..1599) {
                            b += 1
                        }
                        else if(rat in 1600..1999) {
                            c += 1
                        }
                        else if(rat in 2000..2399){
                            d += 1
                        }
                        else if(rat in 2400..2799) {
                            e += 1
                        }
                        else if(rat in 2800..3199) {
                            f += 1
                        }
                        else if(rat in 3200..3500) {
                            g += 1
                        }
                        ans += 1
                    }
                }
                var others = ans - (a + b + c + d + e + f + g)
                tot.setText("Total Submissions: " + ans.toString())
                one.setText(a.toString())
                two.setText(b.toString())
                three.setText(c.toString())
                four.setText(d.toString())
                five.setText(e.toString())
                six.setText(f.toString())
                seven.setText(g.toString())
                xtra.setText(others.toString())
                val pb3 = findViewById<View>(R.id.progressBar3) as ProgressBar
                pb3.visibility = View.INVISIBLE
            },
            { error ->
                val pb3 = findViewById<View>(R.id.progressBar3) as ProgressBar
                pb3.visibility = View.INVISIBLE
                changeIntent();
            }
        )
        queue.add(jsonObjectRequest)
    }
    fun changeIntent() {
        Toast.makeText(applicationContext , "Something Went Wrong!!" , Toast.LENGTH_LONG).show()
        intent = Intent(applicationContext , MainActivity::class.java)
        startActivity(intent)
    }
}