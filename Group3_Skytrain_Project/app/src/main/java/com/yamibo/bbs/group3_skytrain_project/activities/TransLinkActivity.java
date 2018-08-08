package com.yamibo.bbs.group3_skytrain_project.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.yamibo.bbs.group3_skytrain_project.R;

public class TransLinkActivity extends AppCompatActivity {

    //Shu's api key
    private static String mapApiKey="fH8nhLCTC142J3YXmtLC";
    private Button btnGoNearby;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans_link_map);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);
        btnGoNearby = findViewById(R.id.button);
        btnGoNearby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TransLinkActivity.this, NearbyActivity.class);
                startActivity(intent);
            }
        });

    }

}
