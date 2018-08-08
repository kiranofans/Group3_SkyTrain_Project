package com.yamibo.bbs.group3_skytrain_project.activities;

import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ToggleButton;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.yamibo.bbs.group3_skytrain_project.R;
import com.yamibo.bbs.group3_skytrain_project.adapter.MultiViewRecAdapter;
import com.yamibo.bbs.group3_skytrain_project.models.BaseModel;
import com.yamibo.bbs.group3_skytrain_project.models.TranslinkFeed;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Utils.AlertDialogManager;
import Utils.VolleySingleton;

import static Utils.AppConstants.*;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class FragmentNewsFeed extends android.support.v4.app.Fragment implements MultiViewRecAdapter.OnItemClickListener{
    private static final String RSS_NEWSFEED_LINK=
            "https://api.rss2json.com/v1/api.json?rss_url=https%3A%2F%2Fwww.translink.ca%2Fen%2FUtilities%2FWhatsNewRSS.aspx";
    private AlertDialogManager dialogMgr;

    private static RecyclerView feedRecView;
    private static View v;
    private static MultiViewRecAdapter feedRecAdp;
    private static ImageView icImgView;
    private ToggleButton faveBtnOff;

    private static List<BaseModel> feedsList;
    private static ProgressDialog dialog;

    public FragmentNewsFeed() {
    }//Empty Constructor

    @Override
    public View onCreateView
            (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_news_feed, container, false);
        dialogMgr=AlertDialogManager.getInstance(getContext());
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dialog=new ProgressDialog(getContext());
        feedsList = new ArrayList<>();
        feedRecView = (RecyclerView) view.findViewById(R.id.feed_recView);
        feedRecView.setLayoutManager(new LinearLayoutManager(getContext()));

        faveBtnOff=(ToggleButton)view.findViewById(R.id.faveOffBtn);

        loadNewsRSS();

    }
    private void faveBtn(){
        faveBtnOff.setBackgroundDrawable(ContextCompat.getDrawable(getContext()
                ,R.drawable.ic_star_off));
        faveBtnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    private void loadNewsRSS(){
        dialog.setMessage("Loading...");
        dialog.show();
        JsonObjectRequest request=new JsonObjectRequest
                (Request.Method.GET, RSS_NEWSFEED_LINK, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("RESPONSE",response.toString());
                        try {
                            //JSONObject feedObj=response.getJSONObject("feed");
                            JSONArray itemArr=response.getJSONArray("items");
                            for(int i=0;i<itemArr.length();i++){
                                JSONObject itemObj=itemArr.getJSONObject(i);
                                String titles=itemObj.getString("title");
                                String publishDate=itemObj.getString("pubDate");
                                //String link=itemObj.getString("link");
                                String content=itemObj.getString("content");
                                JSONArray categories=itemObj.getJSONArray("categories");
                                String category=categories.getString(0);
                                if(category.equals("News")){
                                        //icImgView.setImageResource(R.drawable.ic_news);
                                        TranslinkFeed feed1=new TranslinkFeed(titles,"Publish Date: "
                                                +publishDate,content,category);
                                        feedsList.add(feed1);
                                }
                            }
                            feedRecAdp=new MultiViewRecAdapter(feedsList,getContext());
                            feedRecView.setAdapter(feedRecAdp);
                            dialog.hide();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                    Log.d("ERROR",error.getMessage());
            }
        });
        VolleySingleton.getInstance(getContext()).addToRequestQueue(request);
    }

    @Override
    public void onItemClick(int position) {

        faveBtn();
    }

    /*private void requestPermission(){
        ActivityCompat.requestPermissions(getActivity(),new String[]{ACCESS_FINE_LOCATION},
                        LOCATION_PERMISSION_REQUEST_CODE);
    }*/
}
