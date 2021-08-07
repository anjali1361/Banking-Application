package com.example.bankapplication.helper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {//constructor for DatabaseHelper class which when called database is created locally

    //methods of SQLiteOpenHelper class
    override fun onCreate(db: SQLiteDatabase) {
        //table created
        db.execSQL("create table $TABLE_NAME (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME VARCHAR,EMAIL VARCHAR,MOBILE BIGINT,IFSC_CODE BIGINT UNIQUE,ACCOUNT_NO BIGINT UNIQUE, BANK_BALANCE INTEGER NOT NULL)")

        db.execSQL("insert into $TABLE_NAME values(1,'Anjali Kumari','anjali@gmail.com',1234567890,45237,120987654321,100000)")
        db.execSQL("insert into $TABLE_NAME values(2,'Sanjana Kumari','sanjana@gmail.com',9087654321,63875,649370987654,200000)")
        db.execSQL("insert into $TABLE_NAME values(3,'Ruhi Kumari','ruhi@gmail.com',1234567890,11111,547488987654,300000)")
        db.execSQL("insert into $TABLE_NAME values(4,'Aaditi Kumari','aaditi@gmail.com',9087654321,22222,113254609857,400000)")
        db.execSQL("insert into $TABLE_NAME values(5,'Aaditya Kumar','aaditya@gmail.com',1234567890,33333,657893451063,500000)")
        db.execSQL("insert into $TABLE_NAME values(6,'Ankita Kumari','ankita@gmail.com',9087654321,44444,784567207488,600000)")
        db.execSQL("insert into $TABLE_NAME values(7,'Dipika Kumari','dipika@gmail.com',1234567890,55555,884578292004,700000)")
        db.execSQL("insert into $TABLE_NAME values(8,'Kriti Kumari','kriti@gmail.com',9087654321,66666,600111111111,800000)")
        db.execSQL("insert into $TABLE_NAME values(9,'Ankush Kumar','ankush@gmail.com',1234567890,77777,222222222222,90000)")
        db.execSQL("insert into $TABLE_NAME values(10,'Ramesh Kumar','ramesh@gmail.com',9087654321,88888,333333333333,20000)")
        db.execSQL("insert into $TABLE_NAME values(11,'Suresh Kumar','suresh@gmail.com',1234567890,99999,444444444444,30000)")
        db.execSQL("insert into $TABLE_NAME values(12,'Mohan Kumar','mohan@gmail.com',9087654321,41111,555555555555,40000)")
        db.execSQL("insert into $TABLE_NAME values(13,'Soham Kumar','soham@gmail.com',9087654321,42222,666666666666,50000)")
        db.execSQL("insert into $TABLE_NAME values(14,'Manjula Kumari','manjula@gmail.com',1234567890,43333,777777777777,60000)")
        db.execSQL("insert into $TABLE_NAME values(15,'Sinchita Kumari','sinchita@gmail.com',9087654321,45555,888888888888,70000)")
        db.execSQL("insert into $TABLE_NAME values(16,'Payal Kumari','payal@gmail.com',1234567890,46666,999999999999,80000)")
        db.execSQL("insert into $TABLE_NAME values(17,'Priya Kumari','priya@gmail.com',9087654321,47777,100000000000,90000)")
        db.execSQL("insert into $TABLE_NAME values(18,'Sakshi Kumari','sakshi@gmail.com',1234567890,48888,122222222222,10000)")
        db.execSQL("insert into $TABLE_NAME values(19,'Amarjit Kumar','amarjit@gmail.com',9087654321,49999,133333333333,20000)")
        db.execSQL("insert into $TABLE_NAME values(20,'Vishal Kumar','vishal@gmail.com',1234567890,40000,144444444444,30000)")
        db.execSQL("insert into $TABLE_NAME values(21,'Bittu Kumar','bittu@gmail.com',9087654321,12345,155555555555,40000)")
    }

    //methods of SQLiteOpenHelper class
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

        //to drop table
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        //creating table again
        onCreate(db)
    }

    val allData: Cursor//Cursor is an interface which provide random read write access to the result set returned by a database query.
        get() {
            val db = this.writableDatabase
            return db.rawQuery("select * from $TABLE_NAME", null)
        }

    fun deleteData(account: String): Boolean {
        val db = this.writableDatabase
        val cursor = db.rawQuery("select * from user_table where ACCOUNT_NO=?", arrayOf(account))
        if(cursor.count>0){
            val result = db.delete(TABLE_NAME, "ACCOUNT_NO = ?", arrayOf(account))
            return if (result == -1) false else true
        }else{
            return false
        }

    }
    fun updateBankBalance(accountNo: String, amount: Int):Boolean{
        val db=this.writableDatabase
        val contentValues=ContentValues()
        contentValues.put(COL_7, amount)
        val cursor = db.rawQuery("select * from $TABLE_NAME where ACCOUNT_NO=?", arrayOf(accountNo.toString()))
        if(cursor.count>0){
            val result = db.update(TABLE_NAME, contentValues, "ACCOUNT_NO = ?", arrayOf(accountNo.toString()))
            return if (result == -1) false else true
        }
        else{
            return false
        }

    }
    companion object {
        const val DATABASE_NAME = "User.db"
        const val TABLE_NAME = "user_table"
        const val COL_7="BANK_BALANCE"
    }
}