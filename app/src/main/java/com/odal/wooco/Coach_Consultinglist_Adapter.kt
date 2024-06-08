import Consult
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.odal.wooco.ChatActivity
import com.odal.wooco.R

class Coach_Consultinglist_Adapter(private val consultList: List<Consult>) : RecyclerView.Adapter<Coach_Consultinglist_Adapter.ConsultViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConsultViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.coach_consultinglist_item, parent, false)
        return ConsultViewHolder(view)
    }

    override fun onBindViewHolder(holder: ConsultViewHolder, position: Int) {
        val consult = consultList[position]
        holder.bind(consult)
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, ChatActivity::class.java).apply {
                putExtra("uid", consult.mentiUid)  // Use mentiUid instead of mainID
                putExtra("name", consult.mentiName)  // Menti name
                putExtra("chat_type", 1)  // Chat type 1 for consultation
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return consultList.size
    }

    inner class ConsultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val mentiNameTextView: TextView = itemView.findViewById(R.id.menti_cosult_name)
        private val lastChatTextView: TextView = itemView.findViewById(R.id.menti_consult_last_chat)

        fun bind(consult: Consult) {
            mentiNameTextView.text = consult.mentiName
            lastChatTextView.text = consult.lastMessage
        }
    }
}
