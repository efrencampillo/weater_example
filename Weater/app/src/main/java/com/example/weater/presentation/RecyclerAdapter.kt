package com.example.weater.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weater.R
import com.example.weater.domain.DataInfo
import kotlinx.android.synthetic.main.time_weater_row.view.*
import java.text.SimpleDateFormat
import java.util.*

class RecyclerAdapter : RecyclerView.Adapter<TimeHolder>() {

    var items: List<DataInfo> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeHolder {
        val inflater = LayoutInflater.from(parent.context)
        return TimeHolder(inflater.inflate(R.layout.time_weater_row, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: TimeHolder, position: Int) {
        holder.onbind(items[position])
    }
}

class TimeHolder(view: View) : RecyclerView.ViewHolder(view) {
    val time: TextView = view.time
    val summary: TextView = view.summary
    val icon: TextView = view.icon
    fun onbind(info: DataInfo) {
        val date = Date(info.time ?: 0)
        val dateFormat = SimpleDateFormat("MM dd yyyy")
        time.text = dateFormat.format(date)
        summary.text = info.summary
        icon.text = info.icon
    }
}