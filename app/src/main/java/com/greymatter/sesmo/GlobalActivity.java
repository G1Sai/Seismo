package com.greymatter.sesmo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.List;
import java.util.Locale;

public class GlobalActivity extends AppCompatActivity {
    FusedLocationProviderClient client;
    TextView tvLocation;
    String url = "http://sesmomapearthquake.epizy.com/";
    int coolorInt = Color.parseColor("#FFBB86FC");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global);

        tvLocation = findViewById(R.id.tvLocation);

        client = LocationServices.getFusedLocationProviderClient(this);

        setLocation();

    }

    private void setLocation() {
        SharedPreferences sharedPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String area = sharedPref.getString("area","null");

        if (!area.equals("null")){
            tvLocation.setText(area);
        }
    }

    public void openLocation(View view) {
        startActivity(new Intent(getApplicationContext(),LocationActivity.class));
        finish();
    }

    public void openWeb(View view) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(url));
        builder.setToolbarColor(coolorInt);

    }


    public void getCurrentLocation(View view) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location!=null){
                    LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());

                    Geocoder geocoder = new Geocoder(GlobalActivity.this, Locale.getDefault());
                    try {
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                        Address obj = addresses.get(0);
                        String add = obj.getAddressLine(0);
                        String currentAddress = obj.getSubAdminArea() + "," + obj.getAdminArea();

                        StringBuilder sb = new StringBuilder();
                        sb.append(obj.getCountryName() + ",");
                        sb.append(obj.getAdminArea() + ",");
                        sb.append(obj.getSubAdminArea() + ",");
                        sb.append(obj.getLocality() + ",");
                        sb.append(obj.getSubThoroughfare() + ".");

                        Log.d("address",sb.toString());

                        SharedPreferences sp = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                        SharedPreferences.Editor et = sp.edit();
                        et.putString("area",currentAddress);
                        et.apply();

                        setLocation();

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 44){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getCurrentLocation();
            }
        }
    }


}