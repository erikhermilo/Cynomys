package com.cynomex.cynomys.cynomys;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;


public class MapsActivity extends AppCompatActivity
        implements
        OnMapReadyCallback,
        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        GoogleMap.OnCameraMoveStartedListener,
        GoogleMap.OnCameraMoveListener,
        GoogleMap.OnCameraMoveCanceledListener,
        GoogleMap.OnCameraIdleListener {


     private GoogleMap mMap;
     private SupportMapFragment mapFrag;
     private LocationRequest mLocationRequest;
     private Location mLastLocation;
     private Marker mCurrLocationMarker;
     private FusedLocationProviderClient mFusedLocationClient;






             @Override
    protected void onCreate(Bundle savedInstanceState) {
         try {
             super.onCreate(savedInstanceState);
             setContentView(R.layout.activity_maps);


             // Obtain the SupportMapFragment and get notified when the map is ready to be used.
             SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                     .findFragmentById(R.id.map);

             mapFragment.getMapAsync(this);

         }catch(Exception e){
             System.out.println("-------------------------------------------------------------------");
             System.out.println("ERROR: " + e.getMessage());
         }


    }
/*
     @Override
     public void onPause() {
         super.onPause();

         //stop location updates when Activity is no longer active
         if (mFusedLocationClient != null) {
             mFusedLocationClient.removeLocationUpdates(mLocationCallback);
         }
     }
*/

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap= googleMap;



        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }



        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        mMap.setOnCameraIdleListener(this);
        mMap.setOnCameraMoveStartedListener(this);
        mMap.setOnCameraMoveListener(this);
        mMap.setOnCameraMoveCanceledListener(this);

        // We will provide our own zoom controls.
        mMap.getUiSettings().setZoomControlsEnabled(false);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);


        Double lat, lon;
        lat=mFusedLocationClient.getLastLocation().getResult().getLatitude();
        lon = mFusedLocationClient.getLastLocation().getResult().getLongitude();


        // You can now create a LatLng Object for use with maps
        LatLng latLng = new LatLng(lat, lon);
        // Show Sydney
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));


    }





     @Override
     public boolean onMyLocationButtonClick() {
         Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
         // Return false so that we don't consume the event and the default behavior still occurs
         // (the camera animates to the user's current position).

         return false;
     }

     @Override
     public void onMyLocationClick(@NonNull Location location) {
         Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();
     }




    public void lugares(View view){

        Intent intent = new Intent(this,lugares.class);
        startActivity(intent);
    }

    public void alertas(View view){

        Intent intent = new Intent(this,altertas.class);
        startActivity(intent);
    }

    public void config(View view){


        Intent intent = new Intent(this,configuracion.class);
        startActivity(intent);
    }


    @Override
    public void onCameraIdle() {

    }

    @Override
    public void onCameraMoveCanceled() {

    }

    @Override
    public void onCameraMove() {

    }

    @Override
    public void onCameraMoveStarted(int i) {

    }

}
