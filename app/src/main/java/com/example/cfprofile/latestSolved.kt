package com.example.cfprofile

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

class latestSolved : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_latest_solved)
        val handle = intent.getStringExtra("handle").toString()
        process3(handle);
    }
    private fun process3(handle : String) {
        var adapter = findViewById<View>(R.id.list) as ListView
        var questions = arrayListOf<String>()
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
                var name = "N/A";
                var pl = "N/A"
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
                    if(url1.getJSONObject(i).getJSONObject("problem").has("name") == true)
                        name = url1.getJSONObject(i).getJSONObject("problem").opt("name").toString()
                    if(url1.getJSONObject(i).has("programmingLanguage") == true)
                        pl = url1.getJSONObject(i).opt("programmingLanguage").toString()
                    val ele = temp2 + temp1;
                    val len1 = arr.size;
                    arr.add(ele)
                    val len2 = arr.size;
                    if(len2 != len1) {
                        if(temp1[0] == '1' || temp1[0] == '2' || temp1[0] == '3' || temp1[0] == '4' || temp1[0] == '5'
                            || temp1[0] == '6' || temp1[0] == '7' || temp1[0] == '8' || temp1[0] == '9')
                                continue
                        if(rat == 0)
                            questions.add(temp2 + " " + temp1 + ". " + name + "\n" + "Ratings: " + "N/A" + "  Lang: " + pl)
                        else
                            questions.add(temp2 + " " + temp1 + ". " + name + "\n" + "Ratings: " + rat.toString() + "  Lang: " + pl)
                        ans += 1;
                    }
                }
                val arrayAdapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,questions)
                adapter.adapter = arrayAdapter
                if(questions.size == 0) {
                    Toast.makeText(applicationContext , "No Accepted submission for " + handle , Toast.LENGTH_LONG).show()
                }
                val pb1 = findViewById<View>(R.id.progressBar5) as ProgressBar
                pb1.visibility = View.INVISIBLE
                adapter.onItemClickListener =
                    AdapterView.OnItemClickListener { parent, view, position, id ->
                        val selectedItemText = parent.getItemAtPosition(position)
                        MyFun(selectedItemText.toString())
                    }
            },
            { error ->
                val pb1 = findViewById<View>(R.id.progressBar5) as ProgressBar
                pb1.visibility = View.INVISIBLE
                changeIntent();
            }
        )
        queue.add(jsonObjectRequest)
    }
    fun MyFun(q : String) {
        if(q.toString() == "") return;
        var cid = ""
        var ind = ""
        val l = q.toString().length - 1
        var cnt = 0
        var temp = ""
        for(i in 0..l) {
            if(q.toString()[i] == ' ' || q.toString()[i] == '.') {
                cnt += 1
                if(cnt == 1) {
                    cid = temp
                    temp = ""
                }
                else if(cnt == 2) {
                    ind = temp
                }
                else if(cnt >= 3) break
            }
            else
                temp += q.toString()[i].toString()

        }
        val openURL = Intent(android.content.Intent.ACTION_VIEW)
        openURL.data = Uri.parse("https://codeforces.com/contest/" + cid + "/problem/" + ind);
        startActivity(openURL)
    }
    fun changeIntent() {
        Toast.makeText(applicationContext , "Something Went Wrong!!" , Toast.LENGTH_LONG).show()
        intent = Intent(applicationContext , MainActivity::class.java)
        startActivity(intent)
    }
}