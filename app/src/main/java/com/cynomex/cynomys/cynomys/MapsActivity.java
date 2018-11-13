package com.cynomex.cynomys.cynomys;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
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
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class MapsActivity extends AppCompatActivity
        implements
        OnMapReadyCallback {


    private GoogleMap mMap;
    private LocationManager locManager;
    private Double myLat, myLon;
    private List<Modelo.Alerta> alertas;
    int idUsuario;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
         try {

             Intent intentLogin = getIntent();
             idUsuario = intentLogin.getIntExtra("idUsuario",0);

             if (idUsuario == 0){
                 Intent intent = new Intent(this,login.class);
                 startActivity(intent);
             }
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

        Segundoplano ss = new Segundoplano();
        ss.execute();

        locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location loc = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        myLat = loc.getLatitude();
        myLon = loc.getLongitude();

        LatLng latLng = new LatLng(myLat, myLon);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
        mMap.addMarker(new MarkerOptions().position(latLng).title("Yo").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));


        for (int i=0; i < alertas.size(); i++){
            Modelo.Alerta obj = alertas.get(i);
            LatLng ubicacion = new LatLng(Double.parseDouble(obj.getLat()), Double.parseDouble(obj.getLon()));

            mMap.addMarker(new MarkerOptions().position(ubicacion).title(String.valueOf(obj.getIdTipoAlerta())).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
        }


        // We will provide our own zoom controls.
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);


    }


    private class Segundoplano extends AsyncTask<Void,Void,Void> {
        @Override
        protected void onPreExecute() {
            alertas = new ArrayList<>();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            leerAlertas();
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {

        }
    }

    public void leerAlertas() {

        String SOAP_ACTION = "http://tempuri.org/GetAllMarcas";
        String METHOD_NAME = "GetAllMarcas";
        String NAMESPACE = "http://tempuri.org/";
        String URL = "http://192.168.137.1:26314/WebService1.asmx";

        try{

            SoapObject request =new SoapObject(NAMESPACE, METHOD_NAME);

            SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelope.dotNet = true;
            soapEnvelope.setOutputSoapObject(request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(SOAP_ACTION, soapEnvelope);


            // SoapObject response = (SoapObject) soapEnvelope.bodyIn;
            // System.out.println("response"+response.toString() + "  "+response.getProperty(0).toString());

            SoapObject resultObj= (SoapObject) soapEnvelope.getResponse();


            if (resultObj!=null){

                for (int i=0; i < resultObj.getPropertyCount() ; i++){
                    SoapObject obj= (SoapObject) resultObj.getProperty(i);

                    Modelo.Alerta modAlerta = new Modelo.Alerta();
                    modAlerta.setLat(obj.getProperty("Lat").toString());
                    modAlerta.setLon(obj.getProperty("Lon").toString());
                    modAlerta.setIdTipoAlerta(Integer.parseInt(obj.getProperty("IdTipoAlerta").toString()));
                    alertas.add(modAlerta);

                }

            }


        }catch (Exception ex){
            System.out.println("------------------------------------");
            System.out.println("Error!!!  "+ ex.getMessage());


        }
    }


    public void lugares(View view){

        Intent intent = new Intent(this,lugares.class);
        startActivity(intent);
    }

    public void alertas(View view){


        Intent intentAlerta = new Intent(this,altertas.class);
        String lon,lat;
        lon= myLon.toString();
        lat=myLat.toString();
        intentAlerta.putExtra("lon",lon);
        intentAlerta.putExtra("lat",lat);
        intentAlerta.putExtra("idUsuario", idUsuario);


        startActivity(intentAlerta);
    }

    public void config(View view){


        Intent intent = new Intent(this,configuracion.class);
        startActivity(intent);
    }



}
