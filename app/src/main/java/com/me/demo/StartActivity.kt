package com.me.demo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.me.demo.fullNav.MainActivityFull
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        button_full.setOnClickListener {
            startActivity(Intent(this, MainActivityFull::class.java))
        }

        button.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }


}
