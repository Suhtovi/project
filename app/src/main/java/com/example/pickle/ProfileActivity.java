package com.example.pickle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ProfileActivity extends AppCompatActivity {

    private Button back;
    DB dbHelper;
    LinearLayout LLMain2_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DB(this);
        setContentView(R.layout.activity_profile);
        back = (Button) findViewById(R.id.back_btn);
        back.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Context context = ProfileActivity.this;
                        Intent intent = new Intent(context, MapsActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

        getMeets();

    }


    public void getMeets() {
        final String LOG_TAG = "myLogs";
        LLMain2_id = (LinearLayout) findViewById(R.id.LLMain2);

        final SQLiteDatabase database = dbHelper.getWritableDatabase();
        final Cursor cursor = database.query(dbHelper.TABLE_NAME, null, null,
                null, null, null, null);
        if (cursor.moveToFirst()) {
            final int Id_Index = cursor.getColumnIndex(dbHelper.KEY_ID);
            int Title_Index = cursor.getColumnIndex(dbHelper.KEY_TITLE);
            int Key_Words_Index = cursor.getColumnIndex(dbHelper.KEY_WORDS);
            int Date_Index = cursor.getColumnIndex(dbHelper.KEY_DATE);
            int Places_Index = cursor.getColumnIndex(dbHelper.KEY_PLACES);
            int Adress_Index = cursor.getColumnIndex(dbHelper.KEY_ADRESS);
            int Age_Limit_Index = cursor.getColumnIndex(dbHelper.KEY_AGE_LIMIT);
            int Theme_Index = cursor.getColumnIndex(dbHelper.KEY_THEME);
            int About_Index = cursor.getColumnIndex(dbHelper.KEY_ABOUT);
            int Latitude_Index = cursor.getColumnIndex(dbHelper.KEY_LATITUDE);
            int Longitude_Index = cursor.getColumnIndex(dbHelper.KEY_LONGITUDE);

            do {
                LinearLayout linearLayout = new LinearLayout(this);
                LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(ViewGroup.
                        LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                LinearLayout.LayoutParams lParams1 = new LinearLayout.LayoutParams(ViewGroup.
                        LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
                LinearLayout.LayoutParams lParams2 = new LinearLayout.LayoutParams(ViewGroup.
                        LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                FrameLayout frameLayout = new FrameLayout(this);
                linearLayout.addView(frameLayout, lParams);
                LinearLayout linearLayout1 = new LinearLayout(this);
                linearLayout1.setOrientation(LinearLayout.HORIZONTAL);
                frameLayout.addView(linearLayout1, lParams1);
                Button profile_btn = new Button(this);
                profile_btn.setBackgroundResource(R.drawable.profile2);
                FrameLayout.LayoutParams params2 = new FrameLayout.LayoutParams(120,
                        120);
                profile_btn.setLayoutParams(params2);
                linearLayout1.addView(profile_btn, params2);
                LinearLayout linearLayout2 = new LinearLayout(this);
                linearLayout2.setOrientation(LinearLayout.VERTICAL);
                linearLayout1.addView(linearLayout2, lParams1);
                TextView Title = new TextView(this);
                Title.setText(cursor.getString(Title_Index));
                Title.setTextColor(Color.parseColor("#48505b"));
                lParams2.setMargins(25,0,0,0);
                linearLayout2.addView(Title, lParams2);
                TextView About = new TextView(this);
                About.setText(cursor.getString(About_Index));
                About.setTextColor(Color.parseColor("#8190a3"));
                linearLayout2.addView(About, lParams2);



                Button unlock_btn = new Button(this);
                unlock_btn.setBackgroundResource(R.drawable.delete);
                unlock_btn.setAlpha((float) 0.15);
                final String id = cursor.getString(Id_Index);
                unlock_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int delCount = database.delete(dbHelper.TABLE_NAME,
                                dbHelper.KEY_ID + " = " + id, null) ;
                        LLMain2_id.removeAllViews();
                        Context context = ProfileActivity.this;
                        Intent intent = new Intent(context, ProfileActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(100, 75);
                params.gravity = Gravity.BOTTOM | Gravity.RIGHT;
                params.setMargins(0,0,25,0);
                frameLayout.addView(unlock_btn, params);
                unlock_btn.setLayoutParams(params);


                TextView Adress = new TextView(this);
                Adress.setText(cursor.getString(Adress_Index));
                Adress.setTextColor(Color.parseColor("#4c5059"));

                LinearLayout linearLayout3 = new LinearLayout(this);
                linearLayout3.setOrientation(LinearLayout.HORIZONTAL);
                ImageView marker = new ImageView(this);

                marker.setImageResource(R.drawable.marker);
                LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(70,
                        70);
                params3.gravity = Gravity.CENTER;
                linearLayout3.addView(marker, params3);
                marker.setLayoutParams(params3);
                linearLayout3.addView(Adress, lParams);
                linearLayout.addView(linearLayout3, lParams);

                linearLayout.setBackgroundColor(Color.parseColor("#eff2f7"));
                lParams.setMargins(20, 10,20, 0);
                LLMain2_id.addView(linearLayout, lParams);
            } while (cursor.moveToNext());

        } else {
            Log.d("mLog", "0 rows");
        }
        cursor.close();
    }



}
