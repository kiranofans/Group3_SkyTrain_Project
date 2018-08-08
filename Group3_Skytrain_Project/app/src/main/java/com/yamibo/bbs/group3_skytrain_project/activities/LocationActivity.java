package com.yamibo.bbs.group3_skytrain_project.activities;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.yamibo.bbs.group3_skytrain_project.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LocationActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    public static final int LOCATION_REQUEST = 500;
    ArrayList<LatLng>listpoints;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        listpoints = new ArrayList<>();
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
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST);
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
           @Override
           public void onMapLongClick(LatLng latLng) {
               // reset market when arealry  2
                if(listpoints.size()==2){
                listpoints.clear();
                mMap.clear();
        }
        //save first point select
        listpoints.add(latLng);
        // Create marker
      MarkerOptions markerOptions = new MarkerOptions();
      markerOptions.position(latLng);
      if (listpoints.size()==1){
          // add first marker to the map
    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
    }else{
    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
    }
        mMap.addMarker(markerOptions);

        if(listpoints.size()==2){
            //create url , request first marker to second marker
            String url = getRequestUrl(listpoints.get(0), listpoints.get(1));
TaskRequestDirection taskRequestDirection= new TaskRequestDirection();
taskRequestDirection.execute(url);

        }
    }
});
    }



    public String getRequestUrl(LatLng origin, LatLng des){
//value of location

String str_org = "origin=" + origin.latitude+ "," + origin.longitude;
//value of dest
String str_des = "destination="+ des.latitude + "," +des.longitude;
//set value enable to sensor
String snsor = "sensor= false";
//find direction
       String mode = "mode=driving";
       //build full para
       String param = str_org +"&"+str_des+ "&"+snsor +"&" +mode;
       //output
       String output ="json";
       //Create URL
       String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + param;
       return  url;




   }
   private  String requestDirection(String reqUrl) throws IOException {
        String resoinseString = "";
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;
           try {
         URL url = new URL(reqUrl);
         httpURLConnection = (HttpURLConnection)url.openConnection();
         httpURLConnection.connect();
         inputStream = httpURLConnection.getInputStream();
         InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
              BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
 StringBuffer stringBuffer = new StringBuffer();
 String line = "";
 while ((line=bufferedReader.readLine()) !=null){
stringBuffer.append(line);
 }
resoinseString = stringBuffer.toString();
bufferedReader.close();
inputStreamReader.close();
       } catch (Exception  e) {
              e.printStackTrace();
          } finally {
              if(inputStream != null){
                  inputStream.close();
              }
             httpURLConnection.disconnect();
          }
       return  resoinseString;
   }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case LOCATION_REQUEST:
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                mMap.setMyLocationEnabled(true);
            }break;
        }
    }

public class TaskRequestDirection extends AsyncTask<String, Void,String >{
    @Override
    protected String doInBackground(String... strings) {
        String responseString = "";
             try{
            responseString = requestDirection(strings[0]);
             } catch (IOException e) {
                 e.printStackTrace();
             }
return  responseString;
    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
// paser json

    }
}
public  class  TaskParser extends AsyncTask<String, Void, List<List<HashMap<String, String>>>  >  {
    @Override
    protected List<List<HashMap<String, String>>> doInBackground(String... strings) {
        JSONObject jsonObject = null;
        List<List<HashMap<String, String>>> routes = null;

        try{
            jsonObject = new JSONObject(strings[0]);
            DirectionsParser directionsParser= new DirectionsParser();
        routes = directionsParser.parse(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
return  routes;
    }
    @Override
    protected void onPostExecute(List<List<HashMap<String, String>>> lists) {
        ArrayList points = null;
        PolygonOptions    polylineOptions = new PolygonOptions();

        for (List<HashMap<String, String>> path: lists){
            points = new ArrayList();


for (HashMap<String, String> point:path){
double lat = Double.parseDouble(point.get("lat"));
double lon = Double.parseDouble(point.get("lon"));
points.add(new LatLng(lat, lon));
}
            polylineOptions.addAll(points);
            polylineOptions.strokeWidth(15);
            polylineOptions.fillColor(Color.BLUE);
            polylineOptions.geodesic(true);
        }

if (polylineOptions != null){
mMap.addPolygon(polylineOptions); // should be convert addpolyline
}else{
            Toast.makeText(getApplicationContext(), "Direction not found", Toast.LENGTH_SHORT).show();
}
    }
}
}
