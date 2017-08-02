package com.example.android.bigbasket.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TaskDbHelper extends SQLiteOpenHelper {

    public TaskDbHelper(Context context) {
        super(context, taskContract.DB_NAME, null, taskContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + taskContract.TaskEntry.TABLE + " ( " +
                taskContract.TaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                taskContract.TaskEntry.COL_TASK_TITLE + " TEXT NOT NULL);";

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + taskContract.TaskEntry.TABLE);
        onCreate(db);
    }
}