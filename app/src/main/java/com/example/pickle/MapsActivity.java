package com.example.pickle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private UiSettings uiSettings;
    private Button add_meet, profile;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    DB dbHelper;
    LinearLayout LLMain_id;
    ArrayList<MarkerOptions> markerOptions = new ArrayList<MarkerOptions>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DB(this);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        add_meet = (Button) findViewById(R.id.add_meet_btn);
        add_meet.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Context context = MapsActivity.this;
                        Intent intent = new Intent(context, CreateMeetActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
        );
        profile = (Button) findViewById(R.id.profile_btn);
        profile.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Context context = MapsActivity.this;
                        Intent intent = new Intent(context, ProfileActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
        );
        getMeets();
    }

    public void getMeets() {
        markerOptions.clear();
        final String LOG_TAG = "myLogs";
        LLMain_id = (LinearLayout) findViewById(R.id.LLMain);

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
                LLMain_id.addView(linearLayout, lParams);


                LatLng cache_marker = new LatLng(Double.parseDouble(cursor.getString
                        (Latitude_Index)),  Double.parseDouble(cursor.getString(Longitude_Index)));
                MarkerOptions markerOption = new MarkerOptions();
                markerOption.position(cache_marker);
                markerOption.title(cursor.getString(Title_Index));
                markerOptions.add(markerOption);
            } while (cursor.moveToNext());

        } else {
            Log.d("mLog", "0 rows");
        }
        cursor.close();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     *
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.
                ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.
                        ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        mMap.setMyLocationEnabled(true);

        uiSettings = mMap.getUiSettings();
        uiSettings.setMyLocationButtonEnabled(true);
        uiSettings.setCompassEnabled(true);
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setMapToolbarEnabled(false);
        LatLng kng = new LatLng(55.82646689393172, 45.04317536789253);
        mMap.addMarker(new MarkerOptions()
                .position(kng)
                .title("Родной НГИЭУ"));

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, true);
        Location location = locationManager.getLastKnownLocation(provider);
        LatLng myposition = new LatLng(location.getLatitude(), location.getLongitude());
        CameraPosition cameraPosition2 = new CameraPosition.Builder()
                .target(myposition)
                .zoom(15)
                .build();
        CameraUpdate cameraUpdate2 = CameraUpdateFactory.newCameraPosition(cameraPosition2);
        mMap.animateCamera(cameraUpdate2);

        for(MarkerOptions object : markerOptions){
            mMap.addMarker(object);
        };


    }






}
