package com.krillinator.apidemo

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView


class Favourite : AppCompatActivity() {

    var advices = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite)

        showList()
    }

    private fun deleteItem(advice: String) {

        val db = DatabaseHandler(this)
        var data = db.readData()

        for (i in 0 until data.size){
            if(data[i].advice == advice) {
                db.deleteData(data[i].id)
                showList()
            }
        }

    }

    private fun showList() {

        val db = DatabaseHandler(this)
        var data = db.readData()

        var adviceList = findViewById<ListView>(R.id.advice_list)

        advices = ArrayList(data.size)


        for (i in 0 until data.size) {
            advices.add(data[i].advice)
        }

        val arrayAdapter: ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_list_item_1, advices)
        adviceList.adapter = arrayAdapter

        adviceList.onItemClickListener = AdapterView.OnItemClickListener {
                parent, view, position, id ->

            val selectedItem = parent.getItemAtPosition(position)
            advices.removeAt(position)
            deleteItem(selectedItem.toString())

        }

    }

}