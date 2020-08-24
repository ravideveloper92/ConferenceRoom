package com.example.conferenceroom.ui.ViewMeeting

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.conferenceroom.R
import com.example.conferenceroom.data.model.Slots
import kotlinx.android.synthetic.main.txt_time.view.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.ArrayList

class SlotAdapter(val slotList: ArrayList<Slots>) : RecyclerView.Adapter<SlotAdapter.SlotVH>() {
    private val TAG = "SlotAdapter"

    inner class SlotVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setData(slotVH: SlotVH, slots: Slots) {
            Log.d(TAG, "setData: "+ slots)
            val begin = slots.start_time
            val end = slots.end_time
            try {
                val sdf2 = SimpleDateFormat("hh.mm aa")
                slotVH.itemView.txt_begin.setText(sdf2.format(begin)+" - ")
                slotVH.itemView.txt_end.setText(sdf2.format(end))
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            slotVH.itemView.txt_title.setText(slots.title)
        }

    }

    override fun getItemCount(): Int {
        return slotList.size
    }

    override fun onBindViewHolder(holder: SlotVH, position: Int) {
        holder.setData(holder,slotList.get(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlotVH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.txt_time, parent, false)
        return SlotVH(v)
    }

}
