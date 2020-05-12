package com.example.homechefs

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import androidx.core.content.contentValuesOf
import java.security.AccessControlContext

val DATABASE_NAME = "MyDB"
val TABLE_NAME = "Users"
val COL_USERNAME = "username"
val COL_PASSWORD = "password"
val COL_EMAIL = "email"
val COL_PHONE = "phone"
val COL_ID = "id"

class DataBaseHandler (val context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1){
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE " + TABLE_NAME + "(" +
                COL_ID + " INTEGER PRIMARY KEY," +
                COL_USERNAME + " VARCHAR(256)," +
                COL_PASSWORD + " VARCHAR(256)," +
                COL_EMAIL + " VARCHAR(256)," +
                COL_PHONE + " INTEGER)" ;

        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
    fun insertData(user: User){
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COL_USERNAME,user.username)
        cv.put(COL_PASSWORD,user.password)
        cv.put(COL_EMAIL,user.email)
        cv.put(COL_PHONE,user.phone)
        var result = db.insert(TABLE_NAME,null,cv)
        if (result == (-1).toLong())
            Toast.makeText(context, "Failed", Toast.LENGTH_LONG).show()
        else
            Toast.makeText(context, "Success", Toast.LENGTH_LONG).show()

    }

}