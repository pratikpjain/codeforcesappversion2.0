package com.example.cfprofile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.cfprofile.Data.MyDb

class Friends : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends2)

        var refresh = findViewById<View>(R.id.refresh) as ImageView

        refresh.setOnClickListener(View.OnClickListener {
            intent = Intent(applicationContext , Friends::class.java)
            finish()
            startActivity(intent)
        })

        var adapter = findViewById<View>(R.id.frnds) as ListView

        var myfriends = arrayListOf<String>()

        val db: MyDb = MyDb(this)

        var list = db.viewFriends();

        for(handle in list) {
            myfriends.add(handle);
        }

        val arrayAdapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,myfriends)
        adapter.adapter = arrayAdapter
        if(myfriends.size == 0) {
            Toast.makeText(applicationContext , "You have no friends!!!", Toast.LENGTH_LONG).show()
        }

        adapter.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val selectedItemText = parent.getItemAtPosition(position)
                openProfile(selectedItemText.toString())
            }
    }

    fun openProfile(handle: String) {

        intent = Intent(applicationContext , DisplayInfo::class.java)
        intent.putExtra("handle", handle)
        startActivity(intent)
    }
}