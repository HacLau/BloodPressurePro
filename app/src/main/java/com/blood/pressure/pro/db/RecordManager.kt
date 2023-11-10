package com.blood.pressure.pro.db

import com.blood.pressure.pro.application
import com.blood.pressure.pro.model.RecordEntity


object RecordManager {
    private val dbHelper: DatabaseHelper by lazy {
        DatabaseHelper(context = application)
    }

    fun insert(data: RecordEntity) {
        dbHelper.writableDatabase.apply {
            execSQL(
                "insert into ${DatabaseHelper.RECORD_TABLE_NAME}(level,title, systolic, diastolic,time) values(?,?,?,?,?)",
                arrayOf<String>("${data.level}", data.title, "${data.systolic}", "${data.diastolic}", "${data.time}")
            )
            close()
        }
    }

    fun update(data: RecordEntity) {
        dbHelper.writableDatabase.apply {
            execSQL(
                "update ${DatabaseHelper.RECORD_TABLE_NAME} set level=?,title=?,systolic=?,diastolic=?,time=? where id=?",
                arrayOf<String>("${data.level}", data.title, "${data.systolic}", "${data.diastolic}", "${data.time}", "${data.id}")
            )
            close()
        }
    }

    fun query(): List<RecordEntity> {
        return mutableListOf<RecordEntity>().let {list->
            dbHelper.readableDatabase.let {
                it.rawQuery("select * from ${DatabaseHelper.RECORD_TABLE_NAME}", null).apply {
                    moveToFirst()
                    while (!isAfterLast) {
                        val recordEntity = RecordEntity(
                            getInt(0),
                            getInt(1),
                            getString(2),
                            getInt(3),
                            getInt(4),
                            getString(5).toLong(),
                        )
                        list.add(recordEntity)
                        moveToNext()
                    }
                    if (!isClosed) {
                        close()
                    }
                }
                it.close()
            }
            list

        }
    }
}