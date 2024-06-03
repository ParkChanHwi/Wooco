package com.odal.wooco

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
//import com.google.firebase.firestore.auth.User
import com.odal.wooco.datamodels.UserDataModel

//채팅 어뎁터
class UserAdapter(private val context: Context, private val userList: ArrayList<UserDataModel>):
    RecyclerView.Adapter<UserAdapter.UserViewHolder>(){

    /**
     * 화면 설정
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view: View = LayoutInflater.from(context).
        inflate(R.layout.menti_coachlist_item, parent, false)

        return UserViewHolder(view)
    }

    /**
     * 데이터 설정
     */
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {

        val currentUser = userList[position]
        holder.nameText.text = currentUser.name

        //아이템 클릭 이벤트 - 채팅창으로 넘어가는 기능
        holder.itemView.setOnClickListener{
            // 이동 화면
            val intent = Intent(context, ChatActivity::class.java)
            // 넘길 데이터
            intent.putExtra("name", currentUser.name)
            intent.putExtra("uid", currentUser.uid)

            context.startActivity(intent)
        }
    }

    /**
     * 데이터 갯수 가져오기
     */
    override fun getItemCount(): Int {
        return userList.size
    }

    class UserViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val nameText: TextView = itemView.findViewById(R.id.name_text_id)
    }
}