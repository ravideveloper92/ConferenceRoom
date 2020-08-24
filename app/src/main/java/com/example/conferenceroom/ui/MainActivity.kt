package com.example.conferenceroom.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.conferenceroom.DatabaseClient
import com.example.conferenceroom.R
import com.example.conferenceroom.data.model.Slots
import com.example.conferenceroom.ui.BookMeeting.BookMeeting
import com.example.conferenceroom.ui.ViewMeeting.ViewMeeting
import com.example.conferenceroom.util.Constants
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_book.setOnClickListener {
            val intent= Intent(
                this,
                BookMeeting::class.java
            )
            startActivity(intent)
        }

        btn_view.setOnClickListener {
            val intent= Intent(
                this,
                ViewMeeting::class.java
            )
            startActivity(intent)
        }
    }

}