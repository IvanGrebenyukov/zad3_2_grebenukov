package com.example.zad3_2_grebenukov

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val timer = object:CountDownTimer(3000, 1000){
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                val intent = Intent(this@MainActivity, LogInActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        timer.start()
    }
}