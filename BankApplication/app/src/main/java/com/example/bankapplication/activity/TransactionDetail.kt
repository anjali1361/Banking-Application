package com.example.bankapplication.activity

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bankapplication.R
import com.example.bankapplication.adapter.TransactionListAdapter
import com.example.bankapplication.helper.TransactionDBHelper
import com.example.bankapplication.model.TransactionModel

class TransactionDetail : AppCompatActivity() {

    private lateinit var transaction_detail_rv: RecyclerView
    var arrayList = ArrayList<TransactionModel>()
    lateinit var toolbar: Toolbar
    var myDb: TransactionDBHelper? = null

    lateinit var emptyList: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_detail)

        mappingViews()
        setUpToolbar()
        displayDatabaseInfo()
        setUpList()

    }

    private fun mappingViews() {
        transaction_detail_rv = findViewById(R.id.transaction_detail_rv)
        emptyList = findViewById(R.id.empty_text)
        myDb = TransactionDBHelper(this)
    }

    private fun setUpList() {
        val layoutManager = LinearLayoutManager(this)
        transaction_detail_rv.layoutManager = layoutManager

        val adapter = TransactionListAdapter(arrayList)
        transaction_detail_rv.adapter = adapter
    }

    private fun setUpToolbar() {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Transaction List"
    }

    fun displayDatabaseInfo() {

        val res = myDb!!.allData

        while (res.moveToNext()) {
            val transactionModelList = TransactionModel(res.getString(1), res.getString(2), res.getInt(3), res.getInt(4))
            arrayList.add(transactionModelList)
        }

        if (arrayList.isEmpty()) {
            emptyList.setVisibility(View.VISIBLE)
        } else {
            emptyList.setVisibility(View.GONE)
        }
    }
}