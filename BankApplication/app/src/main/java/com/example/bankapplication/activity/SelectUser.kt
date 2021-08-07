package com.example.bankapplication.activity

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bankapplication.R
import com.example.bankapplication.adapter.TranferToUserListAdapter
import com.example.bankapplication.helper.DatabaseHelper
import com.example.bankapplication.helper.TransactionDBHelper
import com.example.bankapplication.model.ViewUserModel

class SelectUser : AppCompatActivity(), TranferToUserListAdapter.OnUserListener {
    private lateinit var select_user_rv: RecyclerView
    var arrayList = ArrayList<ViewUserModel>()
    var myDb: DatabaseHelper? = null
    lateinit var toolbar: Toolbar

    var fromUserAccountNo: String? = null
    var toUserAccountNo: String? = null
    var toUserAccountBalance: String? = null
    var fromUserAccountName: String? = null
    var fromUserAccountBalance: String? = null
    var transferAmount: String? = null
    var toUserAccountName: String? = null

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_user)

        myDb = DatabaseHelper(this)
        select_user_rv = findViewById(R.id.select_user_rv)
        toolbar = findViewById(R.id.toolbar)

        getIntentFrom()
        setUpToolbar(toolbar)
        displayDatabaseInfo()
        setUpList()
    }

    private fun getIntentFrom() {
        // Get Intent
        val bundle = intent.extras
        if (bundle != null) {
            fromUserAccountName = bundle.getString("FROM_USER_NAME")
            fromUserAccountNo = bundle.getString("FROM_USER_ACCOUNT_NO")
            fromUserAccountBalance = bundle.getString("FROM_USER_ACCOUNT_BALANCE")
            transferAmount = bundle.getString("TRANSFER_AMOUNT")
        }
    }

    private fun displayDatabaseInfo() {
        val res = myDb!!.allData

        while (res.moveToNext()) {
            val viewUserModelList = ViewUserModel(res.getString(1), res.getString(2), res.getString(3), res.getString(4), res.getString(5), res.getString(6))
            arrayList.add(viewUserModelList)
        }
    }

    private fun setUpList() {
        val layoutManager = LinearLayoutManager(this)
        select_user_rv.layoutManager = layoutManager

        val adapter = TranferToUserListAdapter(this, this, arrayList)
        select_user_rv.adapter = adapter;
    }

    private fun setUpToolbar(toolbar: Toolbar?) {
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Transfer Money"
    }


    private fun calculateAmount() {
        val currentAmount = fromUserAccountBalance
        val transferAmountInt = transferAmount
        val remainingAmount = Integer.parseInt(currentAmount!!) - Integer.parseInt(transferAmountInt!!)
        val increasedAmount = Integer.parseInt(transferAmountInt.toString()) + Integer.parseInt(toUserAccountBalance!!)

        // Update amount in the dataBase
        fromUserAccountNo?.let { DatabaseHelper(this).updateBankBalance(it, remainingAmount) }
        toUserAccountNo?.let { DatabaseHelper(this).updateBankBalance(it, increasedAmount) }
    }

    override fun onUserClick(position: Int) {

        // Insert data into transactions table
        toUserAccountNo = arrayList.get(position).bank_account
        toUserAccountName = arrayList.get(position).user_name
        toUserAccountBalance = arrayList.get(position).balance

        calculateAmount()

        TransactionDBHelper(this).insertTransferData(fromUserAccountName, toUserAccountName, transferAmount?.toInt(), 1)
        Toast.makeText(this, "Transaction Successful!!", Toast.LENGTH_LONG).show()

        startActivity(Intent(this@SelectUser, MainActivity::class.java))
        finish()
    }

    override fun onBackPressed() {
        val builder_exitButton = AlertDialog.Builder(this@SelectUser)
        builder_exitButton.setTitle("Do you want to cancel the transaction?").setCancelable(false)
                .setPositiveButton("yes") { dialogInterface, i -> // Transactions Cancelled
                    val dbHelper = TransactionDBHelper(applicationContext)
                    val db: SQLiteDatabase = dbHelper.getWritableDatabase()
                    val values = ContentValues()
                    values.put(TransactionDBHelper.COLUMN_FROM_NAME, fromUserAccountName)
                    values.put(TransactionDBHelper.COLUMN_TO_NAME, "Transaction Cancelled")
                    values.put(TransactionDBHelper.COLUMN_STATUS, 0)
                    values.put(TransactionDBHelper.COLUMN_AMOUNT, transferAmount)
                    db.insert(TransactionDBHelper.TABLE_NAME, null, values)
                    Toast.makeText(this@SelectUser, "Transaction Cancelled!", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this@SelectUser, MainActivity::class.java))
                    finish()
                }.setNegativeButton("No", null)
        val alertExit = builder_exitButton.create()
        alertExit.show()
    }

}