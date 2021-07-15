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

class submission : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_submission)
        val handle = intent.getStringExtra("handle").toString()
        val hdlname = findViewById<View>(R.id.hdlname) as TextView
        hdlname.setText("Hii, " + handle);
        process(handle);
    }
    private fun process(handle : String) {
        val xtra = findViewById<View>(R.id.xtra) as TextView
        val A = findViewById<View>(R.id.a) as TextView
        val B = findViewById<View>(R.id.b) as TextView
        val C = findViewById<View>(R.id.c) as TextView
        val D = findViewById<View>(R.id.d) as TextView
        val E = findViewById<View>(R.id.e) as TextView
        val F = findViewById<View>(R.id.f) as TextView
        val G = findViewById<View>(R.id.g) as TextView
        val H = findViewById<View>(R.id.h) as TextView
        val I = findViewById<View>(R.id.j) as TextView
        val J = findViewById<View>(R.id.i) as TextView
        val tot = findViewById<View>(R.id.total) as TextView
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
                var a = 0 ; var b = 0;var c = 0 ; var d = 0;var e = 0 ; var f = 0;var g = 0 ; var h = 0;var ii = 0 ; var j = 0;
                val arr = mutableSetOf<String>()
                for(i in 0..len-1) {
                    if(url1.getJSONObject(i).opt("verdict").toString() != "OK") continue
                    var temp1 = "";
                    var temp2 = "";
                    if(url1.getJSONObject(i).getJSONObject("problem").has("index") == true)
                        temp1 = url1.getJSONObject(i).getJSONObject("problem").opt("index").toString()
                    if(url1.getJSONObject(i).getJSONObject("problem").has("contestId") == true)
                        temp2 = url1.getJSONObject(i).getJSONObject("problem").opt("contestId").toString()
                    val ele = temp2 + temp1;
                    val len1 = arr.size;
                    arr.add(ele)
                    val len2 = arr.size;
                    if(len2 != len1) {
                        ans += 1
                        if(temp1[0] == 'A') a += 1;
                        if(temp1[0] == 'B') b += 1;
                        if(temp1[0] == 'C') c += 1;
                        if(temp1[0] == 'D') d += 1;
                        if(temp1[0] == 'E') e += 1;
                        if(temp1[0] == 'F') f += 1;
                        if(temp1[0] == 'G') g += 1;
                        if(temp1[0] == 'H') h += 1;
                        if(temp1[0] == 'I') ii += 1;
                        if(temp1[0] == 'J') j += 1;
                    }
                }
                var others = (ans) - (a + b + c + d + e + f + g + h + ii + j)
                tot.setText("Total Submissions: " + ans.toString())
                A.setText(a.toString())
                B.setText(b.toString())
                C.setText(c.toString())
                D.setText(d.toString())
                E.setText(e.toString())
                F.setText(f.toString())
                G.setText(g.toString())
                H.setText(h.toString())
                I.setText(ii.toString())
                J.setText(j.toString())
                xtra.setText(others.toString())
                val pb = findViewById<View>(R.id.progressBar4) as ProgressBar
                pb.visibility = View.INVISIBLE;
            },
            { error ->
                val pb = findViewById<View>(R.id.progressBar4) as ProgressBar
                pb.visibility = View.INVISIBLE;
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