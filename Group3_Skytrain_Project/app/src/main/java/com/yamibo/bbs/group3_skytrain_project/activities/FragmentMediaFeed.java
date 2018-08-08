package com.yamibo.bbs.group3_skytrain_project.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.yamibo.bbs.group3_skytrain_project.R;
import com.yamibo.bbs.group3_skytrain_project.adapter.MultiViewRecAdapter;
import com.yamibo.bbs.group3_skytrain_project.models.BaseModel;
import com.yamibo.bbs.group3_skytrain_project.models.TranslinkFeed;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Utils.VolleySingleton;

public class FragmentMediaFeed extends Fragment implements MultiViewRecAdapter.OnItemClickListener{
    private RecyclerView mediaRecView;
    private MultiViewRecAdapter recAdapter;
    private List<BaseModel> feedsList;
    private ProgressDialog dialog;
    private static final String RSS_MEDIA_LINK="https://api.rss2json.com/v1/api.json?rss_url=https%3A%2F%2Fwww.translink.ca%2FUtilities%2FMedia-RSS.aspx";
    private static int pos=0;

    private ToggleButton starOffBtnImg,starOnBtnImg;
    private View view;
    public FragmentMediaFeed() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_media_feed, container, false);
    }
    @Override
    public void onViewCreated(View v, Bundle savedInstanceState){
        super.onViewCreated(v,savedInstanceState);
        dialog=new ProgressDialog(getContext());
        feedsList=new ArrayList<>();
        mediaRecView=(RecyclerView) v.findViewById(R.id.media_feed_rec);
        mediaRecView.setLayoutManager(new LinearLayoutManager(getContext()));

        starOffBtnImg =(ToggleButton)v.findViewById(R.id.faveOffBtn);
        starOnBtnImg=(ToggleButton) v.findViewById(R.id.faveOffBtn);
        getMediaFeed();

    }
    private void faveBtn(){
        starOffBtnImg.setChecked(false);
        starOffBtnImg.setBackgroundDrawable(ContextCompat.getDrawable(getContext()
                ,R.drawable.ic_star_off));
        starOffBtnImg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });

    }
    private void getMediaFeed() {
        dialog.setMessage("Loading...");
        dialog.show();
        JsonObjectRequest feedRequest = new JsonObjectRequest(Request.Method.GET, RSS_MEDIA_LINK,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                dialog.setMessage("Loading...");
                dialog.show();
                try {
                    JSONArray items=response.getJSONArray("items");
                    for(int i=0;i<items.length();i++){
                        JSONObject itemsObj=items.getJSONObject(i);
                        TranslinkFeed feeds = new TranslinkFeed(itemsObj.getString("title"),
                                "Publish Date: "+itemsObj.getString("pubDate"),
                                itemsObj.getString("content"), itemsObj.getString("categories"));
                        feedsList.add(feeds);
                    }
                    recAdapter = new MultiViewRecAdapter(feedsList, getContext());
                    mediaRecView.setAdapter(recAdapter);
                    dialog.hide();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", error.getMessage());
            }
        });
        VolleySingleton.getInstance(getContext()).addToRequestQueue(feedRequest);
    }

    @Override
    public void onItemClick(int position) {
        pos=position;
        faveBtn();

    }
}
