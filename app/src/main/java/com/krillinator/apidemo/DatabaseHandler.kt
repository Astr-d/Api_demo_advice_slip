package com.krillinator.apidemo

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import retrofit2.http.DELETE
import java.util.*
import kotlin.collections.ArrayList

const val DATABASE_NAME = "AdviceDB"
const val TABLE_NAME = "advices"
const val COL_ID = "id"
const val COL_ADVICE = "advice"


class DatabaseHandler(var context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null,1) {
    override fun onCreate(db: SQLiteDatabase?) {

        val createTable = "CREATE TABLE $TABLE_NAME (" +
                "$COL_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COL_ADVICE VARCHAR(256))"

        db?.execSQL(createTable)

    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    fun insertData(slip: Slip) {

        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COL_ADVICE, slip.advice )

        val result = db.insert(TABLE_NAME, null, cv)
    }

    @SuppressLint("Range")
    fun readData() : MutableList<Slip> {
        val list: MutableList<Slip> = ArrayList()

        val db = this.readableDatabase
        val query = "Select * from $TABLE_NAME"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                val slip = Slip()
                slip.id = result.getString(result.getColumnIndex(COL_ID)).toInt()
                slip.advice = result.getString(result.getColumnIndex(COL_ADVICE))
                list.add(slip)
            } while (result.moveToNext())
        }

        result.close()
        db.close()
        return list
    }


    fun deleteData(id: Int): Int {

        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COL_ID, id)

        val result = db.delete(TABLE_NAME,"$COL_ID=$id", null)
        db.close()

        return result
    }
}
