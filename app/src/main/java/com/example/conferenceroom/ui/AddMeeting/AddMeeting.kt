package com.example.conferenceroom.ui.AddMeeting

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.conferenceroom.DatabaseClient
import com.example.conferenceroom.R
import com.example.conferenceroom.data.model.Slots
import com.example.conferenceroom.util.Constants
import kotlinx.android.synthetic.main.activity_main2.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executors

class AddMeeting : AppCompatActivity() {
    val timeFormat: DateFormat = SimpleDateFormat("hh:mm aa")
    val timeFormat2: DateFormat = SimpleDateFormat("hh:mm")
    val dateFormat: DateFormat = SimpleDateFormat(Constants.DATE_FORMAT)
    val datetimeFormat: DateFormat = SimpleDateFormat(Constants.TIME_STAMP_FORMAT2)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val bundle=intent.extras
        val slots=bundle?.getParcelable<Slots>("slot")
        slots?.let {
            txt_room.setText(slots.room)
            txt_slot.setText("Slot"+bundle?.getInt("slotID"))
            txt_start_hr.setText(timeFormat.format(slots.start_time).substring(0,2))
            txt_start_min.setText(timeFormat.format(slots.start_time).substring(3,5))
            txt_start_aa.setText(timeFormat.format(slots.start_time).substring(5,8))
            txt_end_hr.setText(timeFormat.format(slots.end_time).substring(0,2))
            txt_end_min.setText(timeFormat.format(slots.end_time).substring(3,5))
            txt_end_aa.setText(timeFormat.format(slots.end_time).substring(5,8))
        }

        button_save.setOnClickListener {
            if (et_title.text.toString().isNullOrBlank())
            {
                Toast.makeText(this, "Please enter Meeting title", Toast.LENGTH_SHORT).show()
            }else  if (txt_start_min.text.toString().toInt()<=60 && txt_end_min.text.toString().toInt()<=60)
            {
                val begin: Date = datetimeFormat.parse(dateFormat.format(slots?.start_time)+" "+txt_start_hr.text.toString()+":"+txt_start_min.text.toString()+""+txt_start_aa.text.toString().toLowerCase())
                val end: Date = datetimeFormat.parse(dateFormat.format(slots?.end_time)+" "+txt_end_hr.text.toString() + ":"+txt_end_min.text.toString()+""+txt_end_aa.text.toString().toLowerCase())
                slots?.apply {
                    this.start_time=begin
                    this.end_time=end
                    title=et_title.text.toString()
                    addSlots(this)
                }
            }else{
                Toast.makeText(this, "Invalid time", Toast.LENGTH_SHORT).show()
            }

        }

    }
    private fun addSlots(task: Slots) {
        Executors.newSingleThreadExecutor().execute({
            DatabaseClient.getInstance(
                applicationContext
            ).appDatabase
                .taskDao()
                ?.insert(task)
            runOnUiThread() {
                Toast.makeText(this, "Slot Book Successfully", Toast.LENGTH_SHORT).show()
            }
            val returnIntent = Intent();
            setResult(Activity.RESULT_OK,returnIntent);
            finish();
        })
    }

}