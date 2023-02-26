package com.example.birthdaycafe.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.content.Context
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.birthdaycafe.R
import com.example.birthdaycafe.data.cafeData


class ListAdapter (private val context: Context) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    var datas = mutableListOf<cafeData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.cafe_list,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])

    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val name: TextView = itemView.findViewById(R.id.name)
        private val located: TextView = itemView.findViewById(R.id.located)
        private val station: TextView = itemView.findViewById(R.id.station)
        private val time: TextView = itemView.findViewById(R.id.time)

        fun bind(item: cafeData) {
            name.text = item.name
            located.text = item.located
            station.text = item.station
            time.text = item.time
        }
    }


}