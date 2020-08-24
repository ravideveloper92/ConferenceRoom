package com.example.conferenceroom.ui.ViewMeeting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.conferenceroom.R
import com.example.conferenceroom.util.Constants
import kotlinx.android.synthetic.main.activity_book_meeting.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class ViewMeeting : AppCompatActivity() {
    private val TAG = "ViewMeeting"
    val dateFormat: DateFormat = SimpleDateFormat(Constants.DATE_FORMAT)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_meeting)
        val date = Date()
        date.setDate(date.getDate() + 1)
        txt_date.setText(dateFormat.format(date))

        val slotFrag =
            SlotsFragment.newInstance(
                Constants.R1
            )
        supportFragmentManager.beginTransaction().replace(R.id.container_slots, slotFrag)
            .commitAllowingStateLoss()

        txt_room1.setOnClickListener {
            txt_room1.setBackgroundColor(resources.getColor(R.color.colorAccent))
            txt_room2.setBackgroundColor(resources.getColor(R.color.grey))
            val slotFrag =
                SlotsFragment.newInstance(
                    Constants.R1
                )
            supportFragmentManager.beginTransaction().replace(R.id.container_slots, slotFrag)
                .commitAllowingStateLoss()

        }
        txt_room2.setOnClickListener {
            txt_room2.setBackgroundColor(resources.getColor(R.color.colorAccent))
            txt_room1.setBackgroundColor(resources.getColor(R.color.grey))
            val slotFrag =
                SlotsFragment.newInstance(
                    Constants.R2
                )
            supportFragmentManager.beginTransaction().replace(R.id.container_slots, slotFrag)
                .commitAllowingStateLoss()

        }

    }
}