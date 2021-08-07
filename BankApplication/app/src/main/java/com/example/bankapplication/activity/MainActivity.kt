package com.example.bankapplication.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.bankapplication.R
import com.example.bankapplication.helper.DatabaseHelper

class MainActivity : AppCompatActivity() {

    private lateinit var all_user: Button
    private lateinit var all_transaction: Button

    var myDb: DatabaseHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mappingViews()
        viewAllUser()
        viewAllTransaction()

    }

    private fun viewAllTransaction() {
        all_transaction.setOnClickListener {
            val intent = Intent(this, TransactionDetail::class.java)
            startActivity(intent)
        }
    }

    private fun viewAllUser() {
        all_user.setOnClickListener {
            val intent = Intent(this, All_User::class.java)
            startActivity(intent)
        }
    }

    private fun mappingViews() {
        myDb = DatabaseHelper(this)

        all_user = findViewById(R.id.all_users)
        all_transaction = findViewById(R.id.all_transaction)
    }
}