package com.example.cfprofile.Data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.cfprofile.Model.User

class MyDb(context: Context): SQLiteOpenHelper(context , DB_NAME , null , DB_VERSION) {

    companion object {
        private val DB_VERSION = 1
        private val DB_NAME = "User"
        private val TABLE_NAME = "User_table"
        private val KEY_ID = "handle"
        private val KEY_RATING = "rating"
        private val KEY_RANK = "rank"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val Create = ("CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " TEXT PRIMARY KEY," + KEY_RATING + " TEXT,"
                + KEY_RANK + " TEXT" + ")")
        Log.d("Create" , "Created :" + Create);
        db?.execSQL(Create)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    fun addFriend(user: User) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, user.handle)
        contentValues.put(KEY_RATING, user.rating)
        contentValues.put(KEY_RANK, user.rank)

        val success = db.insert(TABLE_NAME , null, contentValues)

        db.close()
    }

    fun is_Friend(user: User): Boolean {

        val db = this.readableDatabase
        val sel = "SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_ID + " = " + "'" + user.handle + "'";

        var cursor: Cursor? = null

        try{
            cursor = db.rawQuery(sel, null)
        }catch (e: SQLiteException) {
            db.execSQL(sel)
            return false;
        }
        var is_there = false
        if (cursor.moveToFirst()) {
            do {
                is_there = true
            } while (cursor.moveToNext())
        }
        return is_there
    }

    fun removeFriend(handle: String) {

        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, handle);

        db.delete(TABLE_NAME,   KEY_ID + " = "+ "'" + handle + "'",null)

        db.close();

    }

    fun viewFriends():List<String>{
        val friendList:ArrayList<String> = ArrayList<String>()
        val selectQuery = "SELECT  * FROM " + TABLE_NAME;
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var handle: String
        if (cursor.moveToFirst()) {
            do {
                handle = cursor.getString(cursor.getColumnIndex(KEY_ID + ""))
                val emp= handle.toString();
                friendList.add(emp)
            } while (cursor.moveToNext())
        }
        return friendList
    }
}