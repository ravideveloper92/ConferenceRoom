package com.example.conferenceroom.ui.BookMeeting

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.conferenceroom.R
import com.example.conferenceroom.data.model.Slots
import com.example.conferenceroom.ui.AddMeeting.AddMeeting
import com.example.conferenceroom.util.Constants
import kotlinx.android.synthetic.main.row_slottime.view.*
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class BookSlotAdapter(
    val slotListMap: HashMap<Int, ArrayList<Slots>>,
    val slot: Int,
    val activity: BookMeeting,
    val roomID: String?,
    val posMap: ArrayList<Int>
) : RecyclerView.Adapter<BookSlotAdapter.SlotVH>() {

    init {
        posMap.clear()
        for(key in slotListMap.keys){
            posMap.add(key)
        }
    }

    lateinit  var context: Context

    val slot1 = arrayOf(
        arrayOf("09:00 am", "10:00 am"),
        arrayOf("10:00 am", "11:00 am"),
        arrayOf("11:00 am", "12:00 pm"),
                arrayOf("01:00 pm", "02:00 pm"),
    arrayOf("02:00 pm", "03:00 pm"),
    arrayOf("03:00 pm", "04:00 pm"),
    arrayOf("04:00 pm", "05:00 pm")
        )
    val hourFormat: DateFormat = SimpleDateFormat("hh")
    val timeFormat: DateFormat = SimpleDateFormat("hh:mm")
    val dateFormat: DateFormat = SimpleDateFormat(Constants.DATE_FORMAT)
    val datetimeFormat: DateFormat = SimpleDateFormat(Constants.TIME_STAMP_FORMAT2)


    inner class SlotVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setData(
            slotVH: SlotVH,
            slotsList: ArrayList<Slots>?,
            position: Int,
            id: Int
        ) {

            if (slotsList != null && slotsList.size>0) {
                for (slots in slotsList) {
                    val begin = slots.start_time
                    val end = slots.end_time
                    var textView = TextView(context)
                    textView.setTextColor(context.resources.getColor(R.color.white))

                    try {
                        val sdf2 = SimpleDateFormat("hh.mm aa")
                        textView.setText(sdf2.format(begin) + " - " + sdf2.format(end))
                        slotVH.itemView.ll_slot_conatiner.addView(textView)
                    } catch (e: ParseException) {
                        e.printStackTrace()
                    }
                    textView.setOnClickListener {
                        val bundle= Bundle()
                        bundle.putParcelable("slot",slots)
                        bundle.putInt("slotID",slot)
                        activity.startActivityForResult(Intent(activity,
                            AddMeeting::class.java).putExtras(bundle),22)
                    }

                }
            } else {
                var textView = TextView(context)
                textView.setTextColor(context.resources.getColor(R.color.white))

                textView.setText(slot1.get(id)[0] + " - " + slot1.get(id)[1])

                val date = Date()
                date.setDate(date.getDate() + 1)
                val task1 = Slots()
                task1.createDate = date
                if (true) {
                    task1.start_time = datetimeFormat.parse(dateFormat.format(date) + " " + slot1.get(id)[0])
                    task1.end_time = datetimeFormat.parse(dateFormat.format(date) + " " + slot1.get(id)[1])
                }
                task1.room = roomID
                task1.title = ""
                slotVH.itemView.ll_slot_conatiner.addView(textView)
                textView.setOnClickListener {
                    val bundle= Bundle()
                    bundle.putParcelable("slot",task1)
                    bundle.putInt("slotID",slot)
                    activity.startActivityForResult(Intent(activity,
                        AddMeeting::class.java).putExtras(bundle),22)
                }

            }

        }

    }

    override fun getItemCount(): Int {

        return slotListMap.size
    }

    override fun onBindViewHolder(holder: SlotVH, position: Int) {
        holder.setData(holder,slotListMap.get(posMap.get(position)),position,posMap.get(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlotVH {
        context=parent.context
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_slottime, parent, false)
        return SlotVH(v)
    }

}
