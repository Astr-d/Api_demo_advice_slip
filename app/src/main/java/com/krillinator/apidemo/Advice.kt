package com.krillinator.apidemo

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Advice : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_advice)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.adviceslip.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(AdviceApi::class.java)
        val call = service.getInfo()
        val generatedAdvice = findViewById<TextView>(R.id.generatedAdvice)
        val backBtn = findViewById<Button>(R.id.back_btn)
        val reloadBtn = findViewById<ImageButton>(R.id.reload)
        val listBtn = findViewById<ImageButton>(R.id.listBtn)
        val saveToDb = findViewById<ImageButton>(R.id.db_advice)

        var arrayOfAdvices = arrayOf (generatedAdvice)

        val context = this
        val db = DatabaseHandler(context)

        call.enqueue(object : Callback<AdviceSlip> {
            override fun onResponse(call: Call<AdviceSlip>, response: Response<AdviceSlip>) {
                if (response.isSuccessful) {

                    val showAdvice = response.body()!!

                    val stringBuilder = "${showAdvice.slip.advice}"

                  generatedAdvice.text = stringBuilder
                }
            }
            override fun onFailure(call: Call<AdviceSlip>, t: Throwable) {
                Toast.makeText(applicationContext, t.message.toString(), Toast.LENGTH_LONG).show()
                Log.e("Debug Message",t.stackTraceToString())
            }
        })

        reloadBtn.setOnClickListener{
            val intent = Intent(this, Advice::class.java)
            startActivity(intent)
        }

        saveToDb.setOnClickListener(){
            val slip = Slip(generatedAdvice.text.toString())
            db.insertData(slip)
            Toast.makeText(applicationContext, "Saved", Toast.LENGTH_LONG).show()

        }

        listBtn.setOnClickListener() {
            val intent = Intent(this, Favourite::class.java)
            startActivity(intent)
        }

        backBtn.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

}