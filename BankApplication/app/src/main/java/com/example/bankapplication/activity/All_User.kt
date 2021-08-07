package com.example.bankapplication.activity

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bankapplication.R
import com.example.bankapplication.adapter.AllUserListAdapter
import com.example.bankapplication.helper.DatabaseHelper
import com.example.bankapplication.model.ViewUserModel

class All_User : AppCompatActivity() {

    private lateinit var view_user_rv: RecyclerView
    lateinit var toolbar: Toolbar
    var arrayList = ArrayList<ViewUserModel>()
    var myDb: DatabaseHelper? = null

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all__user)

        mappingViews()
        setUpActionBar()
        displayDatabaseInfo()
        setUpList()
    }

    private fun mappingViews() {
        myDb = DatabaseHelper(this)
        view_user_rv = findViewById(R.id.view_user_rv)
    }

    private fun displayDatabaseInfo() {
        val res = myDb!!.allData

        while (res.moveToNext()) {
            val viewUserModelList = ViewUserModel(res.getString(1), res.getString(2), res.getString(3), res.getString(4), res.getString(5), res.getString(6))
            arrayList.add(viewUserModelList)
        }
    }

    private fun setUpActionBar() {
        toolbar=findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "User List"
    }

    private fun setUpList() {

        val layoutManager = LinearLayoutManager(this)
        view_user_rv.layoutManager = layoutManager

        val adapter = AllUserListAdapter(this, arrayList)
        view_user_rv.adapter = adapter;
    }
}