package com.yamibo.bbs.group3_skytrain_project.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.yamibo.bbs.group3_skytrain_project.R;
import com.yamibo.bbs.group3_skytrain_project.adapter.CardAdapter_NearbyRoutes;
import com.yamibo.bbs.group3_skytrain_project.adapter.CardAdapter_NearbyStops;
import com.yamibo.bbs.group3_skytrain_project.map.MapFragment;
import com.yamibo.bbs.group3_skytrain_project.models.Route;
import com.yamibo.bbs.group3_skytrain_project.models.Stop;
import com.yamibo.bbs.group3_skytrain_project.service.TransLinkService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StopDetailActivity extends AppCompatActivity {


    private CardAdapter_NearbyRoutes mCardAdapterNearbyRoutes;
    private TextView stop_no_tv;
    private TextView stop_name_tv;
    private List<Route> data;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager layoutManager;
    private BusStopNoActivities busStop;

    String selectedStop = "60980";
    int selectedStop2=51042;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_detail);

        busStop=BusStopNoActivities.getInstance();

     setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Stop Details");
        Intent intent = getIntent();
        selectedStop = intent.getStringExtra("stopNo");
        String name = intent.getStringExtra("stopName");
        stop_no_tv = findViewById(R.id.stop_no_label);
        stop_name_tv = findViewById(R.id.stop_name);
        stop_no_tv.setText(selectedStop);
        stop_name_tv.setText(name);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.translink.ca/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TransLinkService service = retrofit.create(TransLinkService.class);
        Call<List<Route>> call = service.getRoutes(""+selectedStop,"fH8nhLCTC142J3YXmtLC", 180);
        call.enqueue(new Callback<List<Route>>() {
            @Override
            public void onResponse(Call<List<Route>> call, Response<List<Route>> response) {
                data = response.body();
                if(data!=null) {
                    mCardAdapterNearbyRoutes = new CardAdapter_NearbyRoutes(data);
                    mRecyclerView.setAdapter(mCardAdapterNearbyRoutes);
                    Log.d("Data call", data.get(0).getRouteName()+" " + data.get(0).getSchedules().get(0).getExpectedCountdown() );

                }
                else
                    Toast.makeText(getApplicationContext(), "no buses for this stop found", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Route>> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.update_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_refresh:
                recreate();
                break;
            default:
                onBackPressed();
                break;
        }


        return true;
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed()
    {Intent intent = new Intent(getApplicationContext(), NearbyActivity.class);
        startActivity(intent);
    }
}
