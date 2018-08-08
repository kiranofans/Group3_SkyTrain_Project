package com.yamibo.bbs.group3_skytrain_project.activities;

import android.Manifest;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.yamibo.bbs.group3_skytrain_project.R;
import com.yamibo.bbs.group3_skytrain_project.adapter.CardAdapter_NearbyStops;
import com.yamibo.bbs.group3_skytrain_project.models.Stop;
import com.yamibo.bbs.group3_skytrain_project.service.TransLinkService;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.subjects.PublishSubject;


public class NearbyActivity extends AppBaseActivity {
    private Toolbar toolbar;
    private List<Stop> data;
    private RecyclerView mRecyclerView;
    private CardAdapter_NearbyStops mCardAdapterNearbyStops;
    private GoogleMap mMap;
    private Marker selMarker;
    private LinearLayoutManager layoutManager;

    private HashMap<Marker, Stop> hashMap;


    public static final int REQ_PERMISSION = 99;

    public static LatLng currentLatLtng =new LatLng(49.265987, -123.115468);

    PublishSubject<LatLng> mObservable = PublishSubject.create();
    private boolean check = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        CardAdapter_NearbyStops.HIGHLIGHTED = -1;
        setToolbar();
        mObservable.map(value -> {
            Log.d("test", "loadNearbyStops: ");
            loadNearbyStops(value);
            loadMap(value);

            return String.valueOf(value);
        }).subscribe(string -> System.out.println(string));

        updateLocation();
    }

    public void setToolbar(){
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Stops Near You");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
   public void loadNearbyStops(LatLng latLng) {

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.translink.ca/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TransLinkService service = retrofit.create(TransLinkService.class);
        double lat = (double)Math.round(latLng.latitude * 1000000d) / 1000000d;
        double longt =  (double)Math.round(latLng.longitude * 1000000d) / 1000000d;
        Call<List<Stop>> call = service.getStop("fH8nhLCTC142J3YXmtLC", lat, longt);
        call.enqueue(new Callback<List<Stop>>() {
            @Override
            public void onResponse(Call<List<Stop>> call, Response<List<Stop>> response) {
                data = response.body();
                if(data!=null) {
                    data = data.subList(0, 10);
                    mCardAdapterNearbyStops = new CardAdapter_NearbyStops(data);
                    mRecyclerView.setAdapter(mCardAdapterNearbyStops);

                    if(mMap!=null)
                    for (Stop stop : data) {
                        Marker marker =
                                mMap.addMarker(new MarkerOptions().position(new LatLng(stop.getLat(), stop.getLongt())).title("" + stop.getStopNo()));
                        hashMap.put(marker, stop);

                    }
                }
                else
                    Toast.makeText(getApplicationContext(), "no translink service in the area", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Stop>> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }

    public void loadMap(LatLng latLng) {
        if (findViewById(R.id.map) != null) {
            hashMap = new HashMap<>();
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(googleMap ->  {
                    mMap = googleMap;
                   if (checkPermission())
                        mMap.setMyLocationEnabled(true);
                   else
                       askPermission();

                    CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng,
                            15);
                    mMap.moveCamera(update);

                    mMap.setOnMarkerClickListener(marker ->  {
                            selMarker = marker;
                            layoutManager.scrollToPositionWithOffset(data.indexOf(hashMap.get(selMarker)), 20);
                            CardAdapter_NearbyStops.HIGHLIGHTED = data.indexOf(hashMap.get(selMarker));
                            mCardAdapterNearbyStops.notifyDataSetChanged();
                            return false;
                    });
            });

        }
    }

    private boolean checkPermission() {
        Log.d("test", "checxPermission ");
        return (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED);
    }

    private void askPermission() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQ_PERMISSION

        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQ_PERMISSION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    updateLocation();
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        mMap.setMyLocationEnabled(true);
                    }

                } else {
                }
                break;
            }
        }
    }

    private void updateLocation(){
        FusedLocationProviderClient mFusedLocationClient = LocationServices
                .getFusedLocationProviderClient(this);
        if(checkPermission())
        {
            Task<Location> locationResult = mFusedLocationClient.getLastLocation();
            locationResult.addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    if (task.isSuccessful()) {
                        Location location = task.getResult();
                        currentLatLtng = new LatLng(location.getLatitude(),
                               location.getLongitude());
                    }
                    mObservable.onNext(currentLatLtng);
                }
            });
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.update_menu, menu);
        return true;
    }

}
