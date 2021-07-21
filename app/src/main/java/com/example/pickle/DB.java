package com.example.pickle;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class DB extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Pickle_DB";
    public static final String TABLE_NAME = "Meetings";

    public static final String KEY_ID = "_id";
    public static final String KEY_TITLE = "Title";
    public static final String KEY_WORDS = "Key_Words";
    public static final String KEY_DATE = "Date";
    public static final String KEY_PLACES = "Places";
    public static final String KEY_ADRESS = "Adress";
    public static final String KEY_AGE_LIMIT = "Age_Limit";
    public static final String KEY_THEME = "Theme";
    public static final String KEY_ABOUT = "About";
    public static final String KEY_LATITUDE = "Latitude";
    public static final String KEY_LONGITUDE = "Longitude";




    public DB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(" + KEY_ID + " integer primary key,"
                + KEY_TITLE + " text," + KEY_WORDS + " text," + KEY_DATE + " text," + KEY_PLACES
        + " integer," +  KEY_ADRESS + " text," + KEY_AGE_LIMIT + " integer," + KEY_THEME + " text,"
                + KEY_ABOUT + " text," + KEY_LATITUDE + " text," + KEY_LONGITUDE + " text" +")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists " + TABLE_NAME);
        onCreate(db);
    }


}
