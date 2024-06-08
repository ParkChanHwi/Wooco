package com.odal.wooco

import Request_Menti
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Coach_menti_request_Adapter(private var itemList: MutableList<Request_Menti>) : RecyclerView.Adapter<Coach_menti_request_Adapter.MentiViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MentiViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.coach_menti_request_item, parent, false)
        return MentiViewHolder(view)
    }

    override fun onBindViewHolder(holder: MentiViewHolder, position: Int) {
        val menti = itemList[position]
        holder.bind(menti)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class MentiViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val mentiname: TextView = itemView.findViewById(R.id.message)

        fun bind(menti: Request_Menti) {
            mentiname.text = menti.message
        }
    }

    // 데이터 업데이트 함수 추가
    fun updateList(newList: MutableList<Request_Menti>) {
        itemList.clear()
        itemList.addAll(newList)
        notifyDataSetChanged()
    }
}
