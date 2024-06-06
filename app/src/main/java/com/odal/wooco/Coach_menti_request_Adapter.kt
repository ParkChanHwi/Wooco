package com.odal.wooco

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.rpc.context.AttributeContext.Request

class Coach_menti_request_Adapter(private val RequestList: List<Request_Menti>) : RecyclerView.Adapter<Coach_menti_request_Adapter.MentiViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MentiViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.coach_menti_request_item, parent, false)
        return MentiViewHolder(view)
    }

    override fun onBindViewHolder(holder: MentiViewHolder, position: Int) {
        val menti = RequestList[position]
        holder.bind(menti)
    }

    override fun getItemCount(): Int {
        return RequestList.size
    }

    inner class MentiViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val mentiname: TextView = itemView.findViewById(R.id.menti_request_name)
        private val menticate: TextView = itemView.findViewById(R.id.menti_request_categori)
        private val mentidate: TextView = itemView.findViewById(R.id.menti_request_date)

        fun bind(menti: Request_Menti) {
            mentiname.text = menti.menti_request_name
            menticate.text = menti.menti_request_categori
            mentidate.text = menti.menti_request_date
        }
    }
}

data class Request_Menti(val menti_request_name: String,val menti_request_categori: String, val menti_request_date: String)