package com.example.conferenceroom.ui.BookMeeting

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.conferenceroom.R
import com.example.conferenceroom.data.model.Slots
import kotlinx.android.synthetic.main.bookfragment_slots.*
import java.text.DateFormat
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
class BookSlotsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    var roomID: String?=null
    var allslotList=ArrayList<Slots>()
    var slotList1Map= HashMap<Int, ArrayList<Slots>>()
    var slotList2Map=HashMap<Int, ArrayList<Slots>>()
    var posMap= ArrayList<Int>()


    val cinema = arrayOf(
        arrayOf("09:00 am", "12:00 pm"),
        arrayOf("01:00 pm", "05:00 pm")
    )

    val hourFormat: DateFormat = SimpleDateFormat("hh")
    val timeFormat: DateFormat = SimpleDateFormat("hh:mm aa")

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
        return inflater.inflate(R.layout.bookfragment_slots, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val linearLayoutManager = GridLayoutManager(activity,2)
        rcv_slot11.layoutManager = linearLayoutManager
        rcv_slot22.layoutManager = GridLayoutManager(activity,2)
        allslotList.clear()
        var count=0
        Executors.newSingleThreadExecutor().execute({
            for(i in 0..2) {
                (activity as BookMeeting).hashMap.get(i)?.let {
                    slotList1Map.put(i, it)
                }
            }
            posMap=ArrayList<Int>()
            posMap.clear()
            for(key in slotList1Map.keys){
                posMap.add(key)
            }
            activity?.runOnUiThread() {
                rcv_slot11.adapter=
                    BookSlotAdapter(
                        slotList1Map,
                        1,
                        activity as BookMeeting,
                        roomID,
                        posMap
                    )
            }
            loadNext(count)
        })

    }

    private fun loadNext(count: Int) {
        var count1 = count
        count1 = 0
        Executors.newSingleThreadExecutor().execute({
            Thread.sleep(300)
            for (i in 3..6) {
                (activity as BookMeeting).hashMap.get(i)?.let {
                    slotList2Map.put(i, it)
                }
            }
            posMap= ArrayList<Int>()
            posMap.clear()
            for (key in slotList2Map.keys) {
                posMap.add(key)
            }
            activity?.runOnUiThread() {
                rcv_slot22.adapter =
                    BookSlotAdapter(
                        slotList2Map,
                        2,
                        activity as BookMeeting,
                        roomID,
                        posMap
                    )
            }
        })
    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(roomID: String) =
            BookSlotsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, roomID)
                }
            }
    }


}