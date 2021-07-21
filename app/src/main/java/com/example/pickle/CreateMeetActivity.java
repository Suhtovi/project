package com.example.pickle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Scroller;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.pickle.WorkaroundMapFragment;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class CreateMeetActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private Geocoder geocoder;
    private UiSettings uiSettings;
    private ScrollView mScrollView;
    private Button back_btn, add_btn, add_adress_btn;
    private EditText Title_et, Key_Words_et, Date_et, Places_et, Adress_et, Age_Limit_et, Theme_et,
            About_et;
    private String Latitude, Longitude;
    DB dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_meeting);
        SupportMapFragment mapFragment = (WorkaroundMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map2);
        mapFragment.getMapAsync(this);
        geocoder = new Geocoder(this, Locale.getDefault());
        dbHelper = new DB(this);
        Adress_et = (EditText) findViewById(R.id.Adress_et);
        add_adress_btn = (Button) findViewById(R.id.add_adress_btn);
        add_adress_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LatLng center = mMap.getCameraPosition().target;
                double latitude = center.latitude;
                double longitude = center.longitude;
                Latitude = Double.toString(latitude);
                Longitude = Double.toString(longitude);
                List<Address> addresses;
                try {
                    addresses = geocoder.getFromLocation(latitude, longitude, 1);
                    String address = addresses.get(0).getAddressLine(0);
                    Adress_et.setText(address);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        back_btn = (Button) findViewById(R.id.back_btn);
        back_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Context context = CreateMeetActivity.this;
                        Intent intent = new Intent(context, MapsActivity.class);
                        startActivity(intent);
                        finish();
                    }
        });
        add_btn = (Button) findViewById(R.id.add_btn);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Title_et = (EditText) findViewById(R.id.Title_et);
                Key_Words_et = (EditText) findViewById(R.id.Key_Words_et);
                Date_et = (EditText) findViewById(R.id.Date_et);
                Places_et = (EditText) findViewById(R.id.Places_et);
                Adress_et = (EditText) findViewById(R.id.Adress_et);
                Age_Limit_et = (EditText) findViewById(R.id.Age_Limit_et);
                Theme_et = (EditText) findViewById(R.id.Theme_et);
                About_et = (EditText) findViewById(R.id.About_et);

                String Title = Title_et.getText().toString();
                String Key_Words = Key_Words_et.getText().toString();
                String Date = Date_et.getText().toString();
                int Places = Integer.parseInt(Places_et.getText().toString());
                String Adress = Adress_et.getText().toString();
                int Age_Limit = Integer.parseInt(Age_Limit_et.getText().toString());
                String Theme = Theme_et.getText().toString();
                String About = About_et.getText().toString();
                SQLiteDatabase database = dbHelper.getWritableDatabase();
                ContentValues contentValues = new ContentValues();

                contentValues.put(dbHelper.KEY_TITLE,  Title);
                contentValues.put(dbHelper.KEY_WORDS,  Key_Words);
                contentValues.put(dbHelper.KEY_DATE,  Date);
                contentValues.put(dbHelper.KEY_PLACES,  Places);
                contentValues.put(dbHelper.KEY_ADRESS,  Adress);
                contentValues.put(dbHelper.KEY_AGE_LIMIT,  Age_Limit);
                contentValues.put(dbHelper.KEY_THEME,  Theme);
                contentValues.put(dbHelper.KEY_ABOUT,  About);
                contentValues.put(dbHelper.KEY_LATITUDE,  Latitude);
                contentValues.put(dbHelper.KEY_LONGITUDE,  Longitude);

                database.insert(dbHelper.TABLE_NAME, null, contentValues);
                //MapsActivity ex = new MapsActivity();
                //ex.getMeets();
                Context context = CreateMeetActivity.this;
                Intent intent = new Intent(context, MapsActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

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

        mScrollView = findViewById(R.id.idScrollView); //parent scrollview in xml, give your scrollview id value
        ((WorkaroundMapFragment) getSupportFragmentManager().findFragmentById(R.id.map2))
                .setListener(new WorkaroundMapFragment.OnTouchListener() {
                    @Override
                    public void onTouch()
                    {
                        mScrollView.requestDisallowInterceptTouchEvent(true);
                    }
                });


    }


}
