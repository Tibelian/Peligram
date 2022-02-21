package com.tibelian.peligram.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.tibelian.peligram.database.PostDbSchema.PostTable;

public class PostBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "postBase.db";

    // constructor of mapper
    public PostBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    // database creator
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        // sql query to create the database
        db.execSQL("CREATE TABLE " + PostTable.NAME + "(" +
                "id integer PRIMARY KEY AUTOINCREMENT, " +
                PostTable.Cols.UUID + "," +
                PostTable.Cols.TITLE + "," +
                PostTable.Cols.IMAGE + "," +
                PostTable.Cols.LIKES +
            ")"
        );
    }

    // trigger on upgrade database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
