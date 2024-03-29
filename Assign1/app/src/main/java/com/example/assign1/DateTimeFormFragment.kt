package com.example.assign1

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.CalendarView
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.fragment.app.Fragment
import com.example.assign1.MainActivity
import com.example.assign1.R
import java.util.*

class DateTimeFormFragment: Fragment(){
    val monthName = arrayListOf("JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC")



    lateinit var mainActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.form_date_time, container, false)
        val c = Calendar.getInstance()
        val dateButton = view.findViewById<Button>(R.id.date_button)
        val timeButton = view.findViewById<Button>(R.id.time_button)

        if( mainActivity.sharedTicketData.ticketDate != "") dateButton.text = mainActivity.sharedTicketData.ticketDate
        if( mainActivity.sharedTicketData.ticketTime != "") timeButton.text = mainActivity.sharedTicketData.ticketTime

        val dateListener = object: DatePickerDialog.OnDateSetListener {
            override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
                val res = "$p3 " + monthName[p2] + " $p1"
                mainActivity.sharedTicketData.ticketDate = res
                mainActivity.update()
                dateButton.text = res
            }
        }

        val timeListener = object: TimePickerDialog.OnTimeSetListener{
            override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
                // AM, PM 설정하기
                val res = timeFormatter(p1, p2)
                mainActivity.sharedTicketData.ticketTime = res
                mainActivity.update()
                timeButton.text = res
            }
        }

        dateButton.setOnClickListener(
            object: View.OnClickListener {
                override fun onClick(p0: View?) {
                    val datePickerDialog = DatePickerDialog(mainActivity, dateListener, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH))
                    datePickerDialog.show()
                }
            }
        )
        timeButton.setOnClickListener(
            object: View.OnClickListener {
                override fun onClick(p0: View?) {
                    val timePickerDialog = TimePickerDialog(mainActivity, timeListener, 0,0,false)
                    timePickerDialog.show()
                }
            }
        )

        return view
    }

    private fun timeFormatter(h:Int, m:Int): String {
        var hString = ""
        var mString = ""
        var ampm = ""

        hString = when {
            h==0 -> "12"
            h<=12 -> "$h"
            else -> (h-12).toString()
        }

        mString = when {
            m<=9 -> "0$m"
            else -> "$m"
        }

        ampm = when {
            h<=11 -> "AM"
            else -> "PM"
        }

        return "$hString:$mString $ampm"
    }




}