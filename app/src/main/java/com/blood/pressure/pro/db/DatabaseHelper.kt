package com.blood.pressure.pro.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DatabaseHelper(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_RECORD_TABLE)
    }


    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
//        if (newVersion > 1) {
//            String alter_sql = "ALTER TABLE " + CONTACTS_TABLE_NAME + " ADD COLUMN " + "phone_new2 VARCHAR;";
//            db.execSQL(alter_sql);
//            alter_sql = "ALTER TABLE " + CONTACTS_TABLE_NAME + " ADD COLUMN " + "phone_new3 VARCHAR;";
//            db.execSQL(alter_sql);
//        }
    }

    companion object {
        const val DATABASE_NAME = "PressurePro.db"
        private const val DATABASE_VERSION = 1
        const val RECORD_TABLE_NAME = "record_table"


        const val CREATE_RECORD_TABLE = "create table " +
                RECORD_TABLE_NAME +
                "(" +
                "id integer primary key autoincrement," +
                "level integer," +
                "title varchar(50)," +
                "systolic integer," +
                "diastolic integer," +
                "time integer" +
                ")"
    }
}

