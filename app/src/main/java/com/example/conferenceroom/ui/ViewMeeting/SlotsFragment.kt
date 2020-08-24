package com.example.conferenceroom.ui.ViewMeeting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.conferenceroom.DatabaseClient
import com.example.conferenceroom.R
import com.example.conferenceroom.data.model.Slots
import kotlinx.android.synthetic.main.fragment_slots.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executors
import kotlin.collections.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SlotsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SlotsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    var roomID: String?=null
    var allslotList=ArrayList<Slots>()
    var slotList1=ArrayList<Slots>()
    var slotList2=ArrayList<Slots>()
    val cinema = arrayOf(
        arrayOf("09:00 am", "12:00 pm"),
        arrayOf("01:00 pm", "05:00 pm")
    )
/*
    val cinema = arrayOf(
        arrayOf(9, 12),
        arrayOf(1, 5)
    )
*/

    val timeFormat: SimpleDateFormat = SimpleDateFormat("hh:mm aa")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            roomID = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_slots, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val linearLayoutManager = LinearLayoutManager(activity)
        rcv_slot1.layoutManager = linearLayoutManager
        rcv_slot2.layoutManager = LinearLayoutManager(activity)
        allslotList.clear()
        slotList1.clear()
        slotList2.clear()
        Executors.newSingleThreadExecutor().execute({
            allslotList = DatabaseClient.getInstance(
                activity?.applicationContext
            ).appDatabase
                ?.taskDao()
                ?.getAll(roomID) as ArrayList<Slots>

            cinema.get(0).let {
                for(slots in allslotList){
                    if (slots.room==roomID) {
                        val begin : Date = timeFormat.parse(it[0])
                        val end : Date = timeFormat.parse(it[1])
                        val slot_begin : Date = timeFormat.parse(timeFormat.format(slots.start_time))
                        val slot_end : Date = timeFormat.parse(timeFormat.format(slots.end_time))
                        if ((slot_begin?.after(begin)!! || slot_begin?.equals(begin)!!) && (slot_end?.equals(end)!! || slot_end?.before(end)!!)) {
                            slotList1.add(slots)
                        }
                    }
                }
                activity?.runOnUiThread() {
                    rcv_slot1.adapter=
                        SlotAdapter(
                            slotList1
                        )
                }

            }
            cinema.get(1).let {
                for(slots in allslotList){
                    if (slots.room==roomID) {
                        val begin : Date = timeFormat.parse(it[0])
                        val end : Date = timeFormat.parse(it[1])
                        val slot_begin : Date = timeFormat.parse(timeFormat.format(slots.start_time))
                        val slot_end : Date = timeFormat.parse(timeFormat.format(slots.end_time))
                        if ((slot_begin?.after(begin)!! || slot_begin?.equals(begin)!!) && (slot_end?.equals(end)!! || slot_end?.before(end)!!)) {
                            slotList2.add(slots)
                        }
                    }
                }
                activity?.runOnUiThread() {
                    rcv_slot2.adapter=
                        SlotAdapter(
                            slotList2
                        )
                }

            }


        })
    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(roomID: String) =
            SlotsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, roomID)
                }
            }
    }


}