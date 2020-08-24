package com.example.conferenceroom.ui.BookMeeting

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.conferenceroom.DatabaseClient
import com.example.conferenceroom.R
import com.example.conferenceroom.data.model.Slots
import com.example.conferenceroom.util.Constants
import kotlinx.android.synthetic.main.activity_book_meeting.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executors
import kotlin.collections.ArrayList

class BookMeeting : AppCompatActivity() {
    val cinema = arrayOf(
        arrayOf(9, 10),
        arrayOf(10, 11),
        arrayOf(11, 12),
        arrayOf(1, 2),
        arrayOf(2, 3),
        arrayOf(3, 4),
        arrayOf(4, 5)
    )

    val  hashMap= HashMap<Int, ArrayList<Slots>>()
    var count = 0
    val hourFormat: DateFormat = SimpleDateFormat("hh")
    val timeFormat: DateFormat = SimpleDateFormat("hh:mm")
    val dateFormat: DateFormat = SimpleDateFormat(Constants.DATE_FORMAT)
    val datetimeFormat: DateFormat = SimpleDateFormat(Constants.TIME_STAMP_FORMAT1)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_meeting)
        val date = Date()
        date.setDate(date.getDate() + 1)
        txt_date.setText(dateFormat.format(date))
        Executors.newSingleThreadExecutor().execute({
            val slotList = DatabaseClient.getInstance(
                applicationContext
            ).appDatabase
                .taskDao()
                ?.getAll(Constants.R1)
            slotList?.let { loadFragment(it,Constants.R1) }

        })

        txt_room1.setOnClickListener {
            txt_room1.setBackgroundColor(resources.getColor(R.color.colorAccent))
            txt_room2.setBackgroundColor(resources.getColor(R.color.grey))
            Executors.newSingleThreadExecutor().execute({
                val slotList = DatabaseClient.getInstance(
                    applicationContext
                ).appDatabase
                    .taskDao()
                    ?.getAll(Constants.R1)
                slotList?.let { it1 -> loadFragment(it1,Constants.R1) }

            })

        }
        txt_room2.setOnClickListener {
            txt_room2.setBackgroundColor(resources.getColor(R.color.colorAccent))
            txt_room1.setBackgroundColor(resources.getColor(R.color.grey))
            Executors.newSingleThreadExecutor().execute({
                val slotList = DatabaseClient.getInstance(
                    applicationContext
                ).appDatabase
                    .taskDao()
                    ?.getAll(Constants.R2)
                slotList?.let { it1 -> loadFragment(it1, Constants.R2) }

            })

        }



    }

    public fun loadFragment(
        slotList: MutableList<Slots>,
        roomID: String
    ) {
        count = 0
        hashMap.clear()
        var match=false
        cinema.forEach {
            val matchSlotList = ArrayList<Slots>()
            val availableSlotList = ArrayList<Slots>()
            for (slots in slotList) {
                val begin: Date = timeFormat.parse(it[0].toString() + ":00")
                val end: Date = timeFormat.parse(it[1].toString() + ":00")
                val slot_begin: Date = timeFormat.parse(timeFormat.format(slots.start_time))
                val slot_end: Date = timeFormat.parse(timeFormat.format(slots.end_time))
              if ((slot_begin?.equals(begin)!!) && (slot_end?.equals(end)!! )) {
                  match=true
                } else {
                  if ((slot_begin?.after(begin)!! || slot_begin?.equals(begin)!!) && (slot_end?.equals(end)!! || slot_end?.before(end)!!)) {
                      slots.start_time = slot_begin
                      slots.end_time = slot_end
                      matchSlotList.add(slots)
                  }
              }
            }
            if (match) {
                count++
                match=false
                return@forEach
            }
            matchSlotList.sortBy { it.start_time }
            var differ:Long=0

            for (index in matchSlotList.indices) {
                    if (index==0) {
                    val begintime = timeFormat.format(hourFormat.parse(it[0].toString()))
                    val begintime_diff: Long? = (matchSlotList.get(index).start_time?.time!!)?.minus(timeFormat.parse(begintime).time)
                    differ= begintime_diff!!
                        addAvailableSlots(begintime_diff, matchSlotList.get(index), begintime, availableSlotList,true)
                }



                if (index == matchSlotList.size - 1) {
                    val endtime = timeFormat.format(hourFormat.parse(it[1].toString()))
                    val endtime_diff: Long? = (timeFormat.parse(endtime).time)?.minus(matchSlotList.get(index).end_time?.time!!)
                    differ= endtime_diff!!
                    addAvailableSlots(
                        endtime_diff,
                        matchSlotList.get(index),
                        endtime,
                        availableSlotList,
                        false
                    )

                } else {
                    val diff: Long? = (matchSlotList.get(index + 1).start_time?.time)?.minus((matchSlotList.get(index).end_time?.time!!))
                    differ= diff!!
                    diff?.let {
                        if (diff > 0 || diff < 0) {
                            val task1 =
                                Slots()
                            task1.createDate = matchSlotList.get(index).end_time
                            task1.start_time = matchSlotList.get(index).end_time
                            task1.end_time = datetimeFormat.parse(dateFormat.format(matchSlotList.get(index).end_time) + " " + timeFormat.format(matchSlotList.get(index + 1).start_time))
                            task1.room = matchSlotList.get(index).room
                            task1.title = ""
                            val print: String = timeFormat.format(matchSlotList.get(index).end_time) + " - " + timeFormat.format(matchSlotList.get(index + 1).start_time)
                            availableSlotList.add(task1)
                        }
                    }
                }
            }
            hashMap.put(count++, availableSlotList)
        }
        val slotFrag =
            BookSlotsFragment.newInstance(
                roomID
            )
        supportFragmentManager.beginTransaction().replace(R.id.container_slots, slotFrag)
            .commitAllowingStateLoss()
    }

    private fun addAvailableSlots(
        diff: Long?,
        slots: Slots,
        time1: String,
        availableSlotList: ArrayList<Slots>,
        first: Boolean
    ) {
        diff?.let {
            if (diff > 0 || diff < 0) {
                val task1 = Slots()
                task1.createDate = slots.end_time
                if (first) {
                    task1.start_time = datetimeFormat.parse(dateFormat.format(slots.end_time) + " " + time1)
                    task1.end_time = datetimeFormat.parse(dateFormat.format(slots.end_time) + " " + timeFormat.format(slots.start_time))
                }else{
                    task1.start_time = slots.end_time
                    task1.end_time = datetimeFormat.parse(dateFormat.format(slots.end_time) + " " + time1)
                }
                var print:String
                if (!first) {
                    print = timeFormat.format(slots.end_time) + " - " + time1
                } else {
                 print = time1  + " - " + timeFormat.format(slots.start_time)
                }
                task1.room = slots.room
                task1.title = ""
                availableSlotList.add(task1)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode==Activity.RESULT_OK) {
            finish()
        }
    }
}