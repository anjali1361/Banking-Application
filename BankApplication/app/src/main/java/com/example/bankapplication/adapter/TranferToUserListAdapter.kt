package com.example.bankapplication.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.bankapplication.R
import com.example.bankapplication.activity.MainActivity
import com.example.bankapplication.helper.DatabaseHelper
import com.example.bankapplication.model.ViewUserModel

class TranferToUserListAdapter(Context: Context, oNUserListener: OnUserListener, arrayList: ArrayList<ViewUserModel>) : RecyclerView.Adapter<
        TranferToUserListAdapter.ViewHolderSelectUser>() {

    var context: Context = Context
    val onUserListener: OnUserListener = oNUserListener
    val list = arrayList
    var myDB: DatabaseHelper? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            TranferToUserListAdapter.ViewHolderSelectUser {

        myDB = DatabaseHelper(context)
        val view = LayoutInflater.from(context).inflate(R.layout.select_user_to_transfer_money_item_view, parent, false)
        val viewHolder = ViewHolderSelectUser(view);
        return viewHolder
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder:
                                  TranferToUserListAdapter.ViewHolderSelectUser, position: Int) {
        val viewUserModel = list[position]
        holder.user_name.text = viewUserModel.user_name
        holder.balance_data.text = viewUserModel.balance.toString()

        holder.delete.setOnClickListener {
            myDB?.deleteData(viewUserModel.bank_account)
            Toast.makeText(context, "User deleted", Toast.LENGTH_SHORT).show()
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }

    inner class ViewHolderSelectUser(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var user_name: TextView
        var balance_data: TextView
        var delete: ImageView
        val ONUserListener: OnUserListener

        init {
            user_name = itemView.findViewById(R.id.user_name)
            balance_data = itemView.findViewById(R.id.balance_data)
            delete = itemView.findViewById(R.id.delete)

            this.ONUserListener = onUserListener
            itemView.setOnClickListener {
                onUserListener.onUserClick(adapterPosition)
            }
        }
    }

    interface OnUserListener {
        fun onUserClick(position: Int)
    }
}